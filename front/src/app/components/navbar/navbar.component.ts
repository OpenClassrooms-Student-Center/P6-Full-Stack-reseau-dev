import {Component, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  sessionService:SessionService = inject(SessionService);
  router:Router = inject(Router);
  mobileMenuOpen = false;
  toggleMobileMenu() {
    this.mobileMenuOpen = !this.mobileMenuOpen;
  }
  isProfileActive(): boolean {
    return this.router.url === '/profile';
  }
}
