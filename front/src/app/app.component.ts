import { Component } from '@angular/core';
import { AuthService } from './pages/auth/services/auth.service';
import { SessionService } from './services/session.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

  
  // connexion() {
  //   this.router.navigate(['/login'])
  // }

  // enregistrement() {
  //   this.router.navigate(['/register'])
  // }

  // theme() {
  //   this.router.navigate(['/theme'])
  // }

  // user() {
  //   this.router.navigate(['/detail'])
  // }
  // edit() {
  //   this.router.navigate(['/edit'])
  // }
}
