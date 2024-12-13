// Importation des dépendances nécessaires
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Themes } from 'src/app/interfaces/themes.interface';

// Déclaration du composant ThemesPageComponent
@Component({
  selector: 'app-themes-page',
  templateUrl: './themes-page.component.html',
  styleUrls: ['./themes-page.component.scss'],
})
export class ThemesPageComponent implements OnInit {
  themes: Themes[] = []; // Propriété pour stocker la liste des thèmes

  // Constructeur du composant avec injection des dépendances
  constructor(private http: HttpClient, private _snackBar: MatSnackBar) {}

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    this.fetchThemes(); // Appel à la méthode pour récupérer les thèmes lors de l'initialisation
  }

  // Méthode pour récupérer la liste des thèmes
  fetchThemes(): void {
    this.http
      .get<{ themes: Themes[] }>('/api/themes') // Envoi d'une requête GET pour récupérer les thèmes
      .subscribe((response) => {
        this.themes = response.themes; // Stockage des thèmes récupérés dans la propriété 'themes'
      });
  }

  // Méthode pour s'abonner à un thème
  subscribeToTheme(themeId: number): void {
    this.http
      .post<Themes[]>('/api/auth/subscribe/' + themeId, {}) // Envoi d'une requête POST pour s'abonner au thème
      .subscribe((response) => {
        // Traiter la réponse de l'API si nécessaire (pas d'action spécifique ici)
        this.openSnackBar('Souscrit avec succès au thème !', 'Fermer'); // Notification de succès
      });
  }

  // Méthode pour afficher une notification
  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action); // Affiche la notification avec le message et l'action spécifiée
  }
}
