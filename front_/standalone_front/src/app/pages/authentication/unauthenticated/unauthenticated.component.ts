import { Component } from '@angular/core';
import {MatButton} from "@angular/material/button";
import {Router, RouterLink} from "@angular/router";
import {TranslocoModule} from "@jsverse/transloco";

@Component({
  selector: 'app-unauthenticated',
  standalone: true,
  imports: [
    MatButton,
    RouterLink,
    TranslocoModule
  ],
  templateUrl: './unauthenticated.component.html',
  styleUrl: './unauthenticated.component.scss'
})
export class UnauthenticatedComponent {

  constructor(
    private router: Router,
  ) {
  }

  goToSignin(){
    this.router.navigate(['/login']);
  }

  goToSignup(){
    this.router.navigate(['/register'])
  }

}
