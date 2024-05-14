import {inject, Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import { SessionService } from "../services/session.service";

@Injectable({
  providedIn: 'root'
})
class PermissionsGuard {

  constructor(
    private router: Router,
    private sessionService: SessionService,
    ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!this.sessionService.isLogged) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}

export const AuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(PermissionsGuard).canActivate(next, state);
}
