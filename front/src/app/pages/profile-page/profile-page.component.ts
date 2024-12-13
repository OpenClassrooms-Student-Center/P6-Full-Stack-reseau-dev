// Importation des dépendances nécessaires
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Subscription } from 'rxjs';
import { Profile, UpdatedUser } from 'src/app/interfaces/profile.interface';
import { Themes } from 'src/app/interfaces/themes.interface';

// Déclaration du composant ProfilePageComponent
@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss'],
})
export class ProfilePageComponent implements OnInit, OnDestroy {
  // Propriétés du composant
  user: Profile = {
    id: 0,
    username: '',
    email: '',
    createdAt: new Date(),
    updatedAt: new Date(),
  }; // Informations de l'utilisateur
  userThemes: Themes[] = []; // Liste des thèmes auxquels l'utilisateur est abonné
  updatedUser: UpdatedUser = { username: '', email: '' }; // Propriétés pour mettre à jour les informations de l'utilisateur
  private meSubscription: Subscription | undefined; // Variable pour gérer l'abonnement aux données de l'utilisateur
  private themesSubscription: Subscription | undefined; // Variable pour gérer l'abonnement aux thèmes

  // Constructeur du composant avec injection des dépendances
  constructor(
    private http: HttpClient, // Service HttpClient pour les requêtes HTTP
    private router: Router, // Service Router pour la navigation
    private authService: AuthService, // Service d'authentification
    private snackbarService: SnackbarService // Service de notifications
  ) {}

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    // Abonnement pour récupérer les informations de l'utilisateur connecté
    this.meSubscription = this.http
      .get<Profile>('/api/auth/me')
      .subscribe((data) => {
        this.user = data; // Assigne les données récupérées à l'utilisateur
        this.updatedUser.username = this.user.username; // Pré-remplit le champ username
        this.updatedUser.email = this.user.email; // Pré-remplit le champ email
      });

    // Abonnement pour récupérer les thèmes auxquels l'utilisateur est abonné
    this.themesSubscription = this.http
      .get<Themes[]>('/api/auth/themes')
      .subscribe((data) => {
        this.userThemes = data; // Assigne les thèmes récupérés à userThemes
      });
  }

  // Méthode pour déconnecter l'utilisateur
  logout() {
    localStorage.removeItem('token'); // Supprime le token de l'utilisateur du stockage local
    this.router.navigateByUrl('/home'); // Redirige l'utilisateur vers la page d'accueil
  }

  // Méthode pour se désabonner d'un thème
  unSubscribeTheme(themeId: number) {
    const headers = { 'Access-Control-Allow-Origin': '*' }; // Spécifie les en-têtes pour la requête
    this.http
      .delete(`/api/auth/unsubscribe/${themeId}`, { headers })
      .subscribe(() => {
        // Met à jour la liste des thèmes en retirant celui désabonné
        this.userThemes = this.userThemes.filter(
          (theme) => theme.id !== themeId
        );
        this.snackbarService.openSnackBar(
          'Modifications enregistrées avec succès !',
          'Fermer'
        ); // Notification de succès
      });
  }

  // Méthode pour sauvegarder les modifications de l'utilisateur
  saveChanges(): void {
    this.http.put('/api/auth/me', this.updatedUser).subscribe(() => {
      this.snackbarService.openSnackBar(
        'Modifications enregistrées avec succès !',
        'Fermer'
      ); // Notification de succès
    });
  }

  // Méthode appelée lors de la destruction du composant
  ngOnDestroy(): void {
    if (this.meSubscription) {
      this.meSubscription.unsubscribe(); // Désabonnement pour éviter les fuites de mémoire
    }
    if (this.themesSubscription) {
      this.themesSubscription.unsubscribe(); // Désabonnement pour éviter les fuites de mémoire
    }
  }
}
