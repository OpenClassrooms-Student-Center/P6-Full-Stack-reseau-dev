import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from './services/auth.service';

// Définition du composant principal de l'application
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html', 
  styleUrls: ['./app.component.scss'] 
})
export class AppComponent {
  
  // Propriété pour gérer l'état de connexion de l'utilisateur
  isLoggedIn: boolean = false;

  // Constructeur pour injecter les services nécessaires: MatSnackBar pour les notifications, et AuthService pour l'authentification
  constructor(private _snackBar: MatSnackBar, private authService: AuthService) {
    this.isLoggedIn = this.authService.isLoggedIn(); // Initialisation de l'état de connexion lors de la création du composant
  }

  // Méthode pour afficher une notification temporaire (snackbar) avec un message et une action
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 1000, // Durée d'affichage de la snackbar en millisecondes
    });
  }  
}
