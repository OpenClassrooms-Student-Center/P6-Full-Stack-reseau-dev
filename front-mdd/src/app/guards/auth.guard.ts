import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { SessionService } from "../shared/services/session.service";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private sessionService: SessionService,
  ) {
  }

  public canActivate(): boolean {
    console.log("canActivate:"+this.sessionService)
    if (!this.sessionService.isLogged) {
      this.router.navigate(['auth/login']);
      return false;
    }
    return true;
  }
}