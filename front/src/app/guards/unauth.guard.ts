import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import { SessionService } from "../services/session.service";

@Injectable({providedIn: 'root'})
export class UnauthGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionService: SessionService,
  ) {
  }
  /**
   * Determines whether the route can be activated based on the user's authentication status.
   * If the user is logged in, they are redirected to the 'articles' route, and the activation is denied.
   * If the user is not logged in, the route is allowed to activate.
   *
   * @return `true` if the route can be activated (i.e., the user is not logged in), `false` if the user is logged in and redirected.
   */
  public canActivate(): boolean {
    if (this.sessionService.isLogged) {
      this.router.navigate(['articles']);
      return false;
    }
    return true;
  }
}
