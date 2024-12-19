import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    standalone: false
})
export class AppComponent implements OnInit {
  title = 'front-mdd';
  showHeader:boolean =false;
  constructor(private router:Router){ }
  ngOnInit(): void {
// Vérifier directement la route active lors de l'initialisation.
this.updateHeaderVisibility(this.router.url);

// Écouter les changements de navigation pour mettre à jour `showHeader`.
this.router.events.subscribe((event) => {
  if (event instanceof NavigationEnd) {
    this.updateHeaderVisibility(event.url);
  }
});  }

  private updateHeaderVisibility(url: string): void {
    // Masquer le header uniquement pour la route '/home'
    if (url !== '/' && url !== '/home') {
      this.showHeader = true;
    } else {
      this.showHeader = false;
    }
  }
}
