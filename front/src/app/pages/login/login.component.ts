// Importation des dépendances nécessaires
import { Component, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LoginForm } from 'src/app/interfaces/login.interface';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ResponseLogin } from 'src/app/interfaces/login.interface';

// Déclaration du composant LoginComponent
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnDestroy {
  private loginSubscription: Subscription | undefined; // Variable pour gérer l'abonnement à la requête de connexion
  formData: LoginForm = { email: '', password: '' }; // Propriété pour stocker les données du formulaire de connexion

  // Constructeur du composant avec injection des dépendances
  constructor(
    private http: HttpClient,
    private router: Router,
    private snackbarService: SnackbarService
  ) {}

  // Méthode pour gérer la connexion
  login(): void {
    this.loginSubscription = this.http
      .post<ResponseLogin>('/api/auth/login', this.formData) // Envoi des données de connexion au serveur
      .subscribe(
        (response) => {
          // Si la réponse contient un token, l'utilisateur est connecté
          if (response && response.token) {
            this.snackbarService.openSnackBar('Connexion réussie !', 'Fermer'); // Notification de succès
            localStorage.setItem('token', response.token); // Stockage du token dans le localStorage
            this.router.navigate(['/article']); // Redirection vers la page des articles
          }
        },
        (error) => {
          console.error('Erreur lors de la connexion:', error); // Affichage de l'erreur dans la console
          this.snackbarService.openSnackBar(
            'Erreur lors de la connexion',
            'Fermer'
          ); // Notification d'erreur
        }
      );
  }

  // Méthode appelée lors de la destruction du composant
  ngOnDestroy(): void {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe(); // Désabonnement pour éviter les fuites de mémoire
    }
  }

  // Méthode pour revenir à la page d'accueil
  goBack(): void {
    this.router.navigate(['/']); // Redirection vers la page d'accueil
  }
}
