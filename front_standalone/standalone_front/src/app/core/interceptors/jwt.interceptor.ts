import {
  HttpErrorResponse, HttpEvent,
  HttpHandler,
  HttpHandlerFn,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest
} from "@angular/common/http";
import {inject, Injectable, OnDestroy} from "@angular/core";
import {AuthService} from "../services/auth.service";
import {JWTService} from "../services/jwt.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {Router} from "@angular/router";
import {catchError, filter, switchMap, take} from "rxjs/operators";
import {BehaviorSubject, Observable, Subject, throwError} from "rxjs";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor, OnDestroy {

  private isRefreshing = false;

  private refreshingSubject: Subject<boolean> = new Subject<boolean>();

  requests: HttpRequest<any>[] = [];


  constructor(private authService: AuthService,
              private jwtService: JWTService,
              private refreshTokenService: RefreshTokenService,
              private router: Router,
              private httpHandler: HttpHandler,
              ) {
    this.refreshingSubject.subscribe( refreshStatus => { if(!refreshStatus){
      this.handleRequests();
    }})
  }

  ngOnDestroy(): void {
        this.refreshingSubject.unsubscribe();
    }

   handleRequests(){
     console.log('handeling requestss : ', this.requests)
      this.requests.forEach(

        request => {
          if (this.jwtService.hasValidToken()) {
            request = request.clone({
              setHeaders: {
                Authorization: `Bearer ${this.jwtService.getToken()}`,
              },
            });
          }
          this.httpHandler.handle(request)
        }
      )
   }

  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    console.log("isRefreshing : ", this.isRefreshing)
    if (this.jwtService.hasValidToken()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.jwtService.getToken()}`,
        },
      });

    }
    return next.handle(request).pipe(
      catchError((error) => {
        if (
          error instanceof HttpErrorResponse &&
          !request.url.includes('token') &&
          error.status === 401
        ) {
          return this.handle401Error(request, next);
        }

        return throwError(() => error);
      })
    );
  }


  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.jwtService.saveToken('');
      if (this.refreshTokenService.hasRefreshToken()) {
        return this.authService.refreshToken().pipe(
          switchMap(() => {
            this.isRefreshing = false;
            console.log('refreshed')
            if (this.jwtService.hasValidToken()) {
              request = request.clone({
                setHeaders: {
                  Authorization: `Bearer ${this.jwtService.getToken()}`,
                },
              });

            }
            return next.handle(request);
          }),
          catchError((error) => {
            this.isRefreshing = false;

            if (error.status == '401') {
              console.log('LOGOUT')
              this.authService.logout();
              this.router.navigate(['/welcome'])
            }

            return throwError(() => error);
          })
        );
      } else {
        this.isRefreshing = false;
        this.router.navigate(['/welcome']);
      }
    }
    return next.handle(request);
  }
}
/*
export const jwtInterceptor: HttpInterceptorFn =
  (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>>
  => { return inject(JwtInterceptor).intercept(req, next)}
*/
