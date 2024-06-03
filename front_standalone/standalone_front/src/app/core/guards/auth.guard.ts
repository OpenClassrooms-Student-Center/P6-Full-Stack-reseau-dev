import {inject, Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {catchError, switchMap} from "rxjs/operators";
import {throwError} from "rxjs";


@Injectable({
  providedIn: 'root'
})
class PermissionsGuard {

  constructor(
    private router: Router,
    private authService: AuthService,
    private refreshTokenService: RefreshTokenService
    ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('AUTH GUARD')
    if (!this.authService.isLoggedIn() && this.refreshTokenService.hasRefreshToken()){
      this.authService.refreshToken().pipe(
        catchError((error) => {
          if (error.status == '401') {
            this.authService.logout();
            this.router.navigate(['/welcome'])
          }

          return throwError(() => error);
        })
      );
    }
    if (!this.authService.isLoggedIn() && !this.refreshTokenService.hasRefreshToken()) {
      this.router.navigate(['welcome']);
      return false;
    }
    return true;
  }
}

export const AuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(PermissionsGuard).canActivate(next, state);
}
