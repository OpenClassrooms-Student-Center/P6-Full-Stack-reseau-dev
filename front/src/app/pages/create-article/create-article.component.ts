// Importation des dépendances nécessaires
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Themes } from 'src/app/interfaces/themes.interface';
import { Subscription } from 'rxjs';

// Déclaration du composant CreateArticleComponent
@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss'],
})
export class CreateArticleComponent implements OnInit, OnDestroy {
  articleForm: FormGroup; // Propriété pour stocker le formulaire de création d'article
  themes: Themes[] = []; // Tableau pour stocker les thèmes récupérés
  private themesSubscription: Subscription | undefined; // Pour gérer l'abonnement à l'API

  // Constructeur du composant avec injection des dépendances
  constructor(
    private formBuilder: FormBuilder, // Pour créer des groupes de formulaires
    private http: HttpClient, // Pour effectuer des requêtes HTTP
    private snackbarService: SnackbarService, // Pour afficher des messages de notification
    private router: Router // Pour la navigation entre les routes
  ) {
    // Initialisation du formulaire avec des contrôles et des validateurs
    this.articleForm = this.formBuilder.group({
      theme: ['', Validators.required], // Champ pour le thème avec validation requise
      title: ['', Validators.required], // Champ pour le titre avec validation requise
      description: [''], // Champ pour la description sans validation
    });
  }

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    this.fetchThemes(); // Appel de la méthode pour récupérer les thèmes disponibles
  }

  // Méthode pour récupérer les thèmes depuis l'API
  fetchThemes(): void {
    // Effectuer une requête GET pour obtenir la liste des thèmes
    this.themesSubscription = this.http
      .get<{ themes: Themes[] }>('/api/themes')
      .subscribe(
        (response) => {
          this.themes = response.themes; // Assignation des thèmes récupérés à la propriété themes
        },
        (error) => {
          console.error('Erreur lors de la récupération des thèmes:', error); // Gestion des erreurs
        }
      );
  }

  // Méthode appelée lors de la soumission du formulaire
  onSubmit(): void {
    if (this.articleForm.valid) {
      // Vérification si le formulaire est valide
      // Récupération des données du formulaire
      const articleData = {
        title: this.articleForm.get('title')?.value, // Récupération du titre
        description: this.articleForm.get('description')?.value, // Récupération de la description
        theme: this.articleForm.get('theme')?.value, // Récupération du thème
      };
      // Envoi des données du nouvel article au serveur
      this.http.post('/api/articles', articleData).subscribe(
        (response: any) => {
          this.snackbarService.openSnackBar(
            'Article créé avec succès',
            'Fermer'
          ); // Notification de succès
          this.router.navigate(['/article']); // Redirection vers la liste des articles
        },
        (error: any) => {
          console.error("Erreur lors de la création de l'article:", error); // Gestion des erreurs
        }
      );
    } else {
      this.snackbarService.openSnackBar('Le formulaire est invalide', 'Fermer'); // Notification si le formulaire est invalide
    }
  }

  // Méthode appelée avant la destruction du composant
  ngOnDestroy(): void {
    // Désabonnement de l'observable pour éviter les fuites de mémoire
    if (this.themesSubscription) {
      this.themesSubscription.unsubscribe();
    }
  }

  // Méthode pour revenir à la liste des articles
  goBack(): void {
    this.router.navigate(['/article']); // Redirection vers la liste des articles
  }
}
