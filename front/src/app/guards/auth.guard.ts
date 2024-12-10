import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import { SessionService } from "../services/session.service";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionService: SessionService,
  ) {
  }
  /**
   * Determines whether the route can be activated based on the user's authentication status.
   * If the user is not logged in, they are redirected to the 'login' route, and the activation is denied.
   * If the user is logged in, the route is allowed to activate.
   *
   * @return `true` if the route can be activated (i.e., the user is logged in), `false` if the user is not logged in and redirected to the login page.
   */
  public canActivate(): boolean {
    if (!this.sessionService.isLogged) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
