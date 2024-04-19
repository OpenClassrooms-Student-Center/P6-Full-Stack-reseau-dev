import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { ThemeService } from "../pages/theme/service/theme.service";
// import { SessionService } from "../services/session.service";

@Injectable({providedIn: 'root'})
export class UnauthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private themeService: ThemeService,
  ) {
  }

  public canActivate(): boolean {
    // if (this.sessionService.isLogged) {
    //   this.router.navigate(['rentals']);
    //   return false;
    // }
    return true;
  }
}