import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {AuthService} from "../features/auth/services/auth.service";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionService: AuthService,
  ) {
  }

  public canActivate(): boolean {
    if (!this.sessionService.isLogged()) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
