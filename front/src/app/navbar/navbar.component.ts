import { Component, HostListener } from '@angular/core';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isMenuOpen: boolean = false;
  isResponsiveView: boolean = false;
  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isResponsiveView = window.innerWidth <= 768;
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}