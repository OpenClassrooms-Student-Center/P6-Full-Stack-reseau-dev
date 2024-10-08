// Importation des modules nécessaires
import { Component, OnInit, HostListener } from '@angular/core';
import { AuthService } from '../../app/services/auth.service';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

// Déclaration du composant Navbar
@Component({
  selector: 'app-navbar', 
  templateUrl: './navbar.component.html', 
  styleUrls: ['./navbar.component.scss'], 
})
export class NavbarComponent implements OnInit {
  // Variables d'état pour gérer l'authentification et l'affichage
  isLoggedIn: boolean = false; 
  isMenuOpen: boolean = false; 
  isResponsiveView: boolean = false; 
  isLoginPage: boolean = false; // Indique si l'utilisateur est sur la page de connexion
  isRegisterPage: boolean = false; // Indique si l'utilisateur est sur la page d'inscription
  isArticlesPage: boolean = false; // Indique si l'utilisateur est sur la page des articles
  isThemesPage: boolean = false; // Indique si l'utilisateur est sur la page des thèmes
  isProfilePage: boolean = false; // Indique si l'utilisateur est sur la page de profil

  // Injection des services AuthService et Router dans le constructeur
  constructor(private authService: AuthService, private router: Router) {}

  // Méthode d'initialisation du composant
  ngOnInit(): void {
    // Vérifie si l'utilisateur est connecté
    this.isLoggedIn = this.authService.isLoggedIn();
    // Vérifie la taille de la fenêtre pour la vue responsive
    this.checkResponsiveView();

    // Écoute les événements de navigation pour vérifier l'URL actuelle
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        // Vérifie sur quelle page l'utilisateur se trouve
        this.checkIfLoginPage();
        this.checkIfRegisterPage();
        this.checkIfArticlesPage();
        this.checkIfThemesPage(); // Vérifiez si vous êtes sur la page de thèmes
        this.checkIfProfilePage();
      });
  }

  // Écouteur d'événements pour la redimensionnement de la fenêtre
  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    this.checkResponsiveView(); // Vérifie la taille de la fenêtre à chaque redimensionnement
  }

  // Vérifie si la vue est en mode responsive
  checkResponsiveView() {
    this.isResponsiveView = window.innerWidth <= 768; // Mode responsive pour les écrans <= 768px
  }

  // Méthode pour ouvrir ou fermer le menu
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  // Méthode pour se déconnecter
  logout() {
    this.authService.logout(); // Appelle le service de déconnexion
    this.isLoggedIn = false; // Met à jour l'état de connexion
  }

  // Méthodes privées pour vérifier la page actuelle
  private checkIfLoginPage() {
    this.isLoginPage = this.router.url === '/login';
  }

  private checkIfRegisterPage() {
    this.isRegisterPage = this.router.url === '/register';
  }

  private checkIfArticlesPage() {
    this.isArticlesPage = this.router.url === '/article'; 
  }

  private checkIfThemesPage() {
    this.isThemesPage = this.router.url === '/themes'; 
  }

  private checkIfProfilePage() {
    this.isProfilePage = this.router.url === '/profile';
  }
}
