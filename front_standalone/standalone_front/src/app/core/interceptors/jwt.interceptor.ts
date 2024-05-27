import {
  HttpErrorResponse, HttpEvent,
  HttpHandler,
  HttpHandlerFn,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest
} from "@angular/common/http";
import {inject, Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";
import {JWTService} from "../services/jwt.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {Router} from "@angular/router";
import {catchError, switchMap} from "rxjs/operators";
import {Observable, throwError} from "rxjs";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  constructor(private authService: AuthService,
              private jwtService: JWTService,
              private refreshTokenService: RefreshTokenService,
              private router: Router,
              ) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    console.log('Interceptor')
    if (this.jwtService.hasValidToken()) {
      console.log('Valid')
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.jwtService.getToken()}`,
        },
      });

    }
    console.log('after check valid token', request)
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

      if (this.refreshTokenService.hasRefreshToken()) {
        console.log('Refresh')
        return this.authService.refreshToken().pipe(
          switchMap(() => {
            this.isRefreshing = false;
            if (this.jwtService.hasValidToken()) {
              console.log('Valid')
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

            if (error.status == '403') {
              this.router.navigate(['/login'])
            }

            return throwError(() => error);
          })
        );
      } else {
        this.router.navigate(['/login']);
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
