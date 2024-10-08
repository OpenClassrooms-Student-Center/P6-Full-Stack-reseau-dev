// Importation des modules nécessaires depuis Angular
import { Injectable } from '@angular/core'; // Injectable pour créer des services
import { MatSnackBar } from '@angular/material/snack-bar'; // MatSnackBar pour afficher des notifications

// Déclaration du service SnackbarService
@Injectable({
  providedIn: 'root' // Indique que ce service est fourni à l'ensemble de l'application
})
export class SnackbarService {
  // Injection de MatSnackBar via le constructeur pour afficher les notifications
  constructor(private snackBar: MatSnackBar) { }

  // Méthode pour ouvrir une notification Snackbar
  openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, { // Appel de la méthode open de MatSnackBar
      duration: 2000, // Durée d'affichage de la notification (en millisecondes)
    });
  }
}
