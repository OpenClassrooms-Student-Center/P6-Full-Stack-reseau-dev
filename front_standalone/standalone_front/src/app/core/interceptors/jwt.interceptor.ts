import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { Injectable } from "@angular/core";
import {AuthService} from "../services/auth.service";
import {JWTService} from "../services/jwt.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {Router} from "@angular/router";
import {catchError, switchMap} from "rxjs/operators";
import {throwError} from "rxjs";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  constructor(private authService: AuthService,
              private jwtService: JWTService,
              private refreshTokenService: RefreshTokenService,
              private router: Router,
              ) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler) {

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

      if (this.refreshTokenService.hasRefreshToken()) {
        return this.authService.refreshToken().pipe(
          switchMap(() => {
            this.isRefreshing = false;

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
      }
    }

    return next.handle(request);
  }

}
