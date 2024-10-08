// Importation des dépendances nécessaires
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { RegisterForm } from 'src/app/interfaces/register.interface';
import { Subscription } from 'rxjs';

// Déclaration du composant RegisterComponent
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit, OnDestroy {
  // Propriétés du composant
  formData: RegisterForm = { email: '', password: '', username: '' }; // Formulaire de données d'inscription
  private registerSubscription: Subscription | undefined; // Variable pour gérer l'abonnement lors de l'inscription

  // Constructeur du composant avec injection des dépendances
  constructor(
    private http: HttpClient,
    private router: Router,
    private snackbarService: SnackbarService
  ) {}

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {}

  // Méthode pour gérer l'inscription de l'utilisateur
  register(): void {
    // Validation de l'email
    if (!this.validateEmail(this.formData.email)) {
      this.snackbarService.openSnackBar('Email invalide', 'Fermer'); // Notification d'email invalide
      return; // Sort de la méthode
    }

    // Validation du mot de passe
    if (!this.validatePassword(this.formData.password)) {
      this.snackbarService.openSnackBar('Mot de passe invalide ', 'Fermer'); // Notification de mot de passe invalide
      return; // Sort de la méthode
    }

    // Envoi des données d'inscription au serveur
    this.registerSubscription = this.http
      .post('/api/auth/register', this.formData)
      .subscribe(
        (response) => {
          this.router.navigate(['/login']); // Redirection vers la page de connexion après une inscription réussie
        },
        (error) => {
          location.reload(); // Recharger la page en cas d'erreur
        }
      );
  }

  // Méthode appelée lors de la destruction du composant
  ngOnDestroy(): void {
    if (this.registerSubscription) {
      this.registerSubscription.unsubscribe(); // Désabonnement pour éviter les fuites de mémoire
    }
  }

  // Méthode privée pour valider l'email
  private validateEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // Expression régulière pour valider l'email
    return emailRegex.test(email); // Retourne vrai si l'email est valide
  }

  // Méthode privée pour valider le mot de passe
  private validatePassword(password: string): boolean {
    const passwordRegex =
      /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/; // Expression régulière pour valider le mot de passe
    return passwordRegex.test(password); // Retourne vrai si le mot de passe est valide
  }

  // Méthode pour revenir à la page précédente
  goBack(): void {
    this.router.navigate(['/']); // Redirection vers la page d'accueil
  }
}
