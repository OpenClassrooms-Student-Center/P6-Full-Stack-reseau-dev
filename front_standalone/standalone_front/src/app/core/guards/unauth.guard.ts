import {inject, Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";


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
      let outcome = false;
      this.authService.refreshToken().pipe(
        catchError((error) => {
          if (error.status == '401') {
            console.log('LOGOUT guard')
            this.authService.logout();
            this.router.navigate(['/welcome'])
          }
          console.log('return error')
          outcome = true;
          return throwError(() => error);
        })
      );
      console.log('goes to home anyway,  outcome : ', outcome)
      return outcome;
    }
    return true;
  }
}

export const NoAuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(NoPermissionsGuard).canActivate(next, state);
}
