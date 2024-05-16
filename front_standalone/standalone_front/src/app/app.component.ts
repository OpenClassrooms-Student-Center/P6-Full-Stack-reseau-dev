import {Component, Inject, PLATFORM_ID} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {CommonModule, isPlatformBrowser} from "@angular/common";
import {SidenavComponent} from "./shared/layout/sidenav/sidenav.component";
import {HeaderComponent} from "./shared/layout/header/header.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HeaderComponent, SidenavComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Mdd-blog';

  constructor(public router: Router,
              @Inject(PLATFORM_ID) private platformId: Object) {
    if (isPlatformBrowser(this.platformId)) {
      const darkModeOn = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
      if (darkModeOn) {
        document.body.classList.add('dark-theme');
      }
    }
  }
}
