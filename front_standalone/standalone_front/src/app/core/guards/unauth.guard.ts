import {inject, Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import { SessionService } from "../services/session.service";

@Injectable({providedIn: 'root'})
class NoPermissionsGuard {

  constructor(
    private router: Router,
    private sessionService: SessionService,
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.sessionService.isLogged) {
      this.router.navigate(['home']);
      return false;
    }
    return true;
  }
}

export const NoAuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(NoPermissionsGuard).canActivate(next, state);
}
