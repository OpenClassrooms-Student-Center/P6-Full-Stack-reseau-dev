// Importation des dépendances nécessaires
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

// Déclaration du composant HomeComponent
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  isLoggedIn: boolean = false; // Propriété pour suivre l'état de connexion de l'utilisateur

  // Constructeur du composant avec injection des dépendances
  constructor(private router: Router, private authService: AuthService) {}

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn(); // Vérifie l'état de connexion de l'utilisateur via AuthService
  }

  // Méthode pour rediriger l'utilisateur vers la page de connexion
  redirectToLogin(): void {
    this.router.navigate(['/login']); // Utilisation du service Router pour naviguer vers la page de connexion
  }

  // Méthode pour rediriger l'utilisateur vers la page d'inscription
  redirectToRegister(): void {
    this.router.navigate(['/register']); // Utilisation du service Router pour naviguer vers la page d'inscription
  }
}
