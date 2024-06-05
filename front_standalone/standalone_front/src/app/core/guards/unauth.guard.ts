import {inject, Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {catchError, switchMap} from "rxjs/operators";
import {tap, throwError} from "rxjs";


@Injectable({providedIn: 'root'})
class NoPermissionsGuard {

  constructor(
    private router: Router,
    private authService: AuthService,
    private refreshTokenService: RefreshTokenService
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('UNAUTH GUARD')
    if (this.authService.isLoggedIn()) {
      console.log('tohome')
      this.router.navigate(['home']);
      return false;
    }
    if (this.refreshTokenService.hasRefreshToken()) {
      let outcome = true;
      console.log('refresh')
      this.authService.refreshToken().subscribe({
        next: (body) => {
          console.log('refresh success')
          this.router.navigate(['/home'])
          outcome = false;
          console.log('outcome : ', outcome)
          return outcome;
        },
        error: (error) => {
          if (error.status == '401') {
            console.log('refresh unsuccess')
            this.authService.logout();
          }
          console.log('return error')
          outcome = true;
          console.log('outcome : ', outcome)
          return outcome;
        }
    });

    }
    return true;
  }
}

export const NoAuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(NoPermissionsGuard).canActivate(next, state);
}
