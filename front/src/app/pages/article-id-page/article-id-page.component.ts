// Importation des dépendances nécessaires
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Article } from 'src/app/interfaces/article.interface';
import { Subscription } from 'rxjs';

// Déclaration de l'interface FormData pour stocker les messages
interface FormData {
  message: string; // Propriété pour le message
}

// Déclaration du composant ArticleIdPageComponent
@Component({
  selector: 'app-article-id-page',
  templateUrl: './article-id-page.component.html',
  styleUrls: ['./article-id-page.component.scss'],
})
export class ArticleIdPageComponent implements OnInit, OnDestroy {
  // Déclaration des propriétés du composant
  article: Article | undefined; // Propriété pour stocker l'article récupéré
  formData: FormData = {
    // Initialisation des données du formulaire
    message: '', // Message initial vide
  };
  private articleSubscription: Subscription | undefined; // Pour gérer l'abonnement à l'API

  // Constructeur du composant avec injection des dépendances
  constructor(
    private route: ActivatedRoute, // Pour accéder aux paramètres de l'URL
    private http: HttpClient, // Pour effectuer des requêtes HTTP
    private router: Router, // Pour la navigation entre les routes
    private snackbarService: SnackbarService // Pour afficher des messages de notification
  ) {}

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    // Souscription aux paramètres de l'URL pour récupérer l'ID de l'article
    this.route.params.subscribe((params) => {
      const id = params['id']; // Récupération de l'ID depuis les paramètres de la route
      if (id) {
        this.fetchArticle(id); // Appel de la méthode fetchArticle si un ID est présent
      }
    });
  }

  // Méthode appelée avant la destruction du composant
  ngOnDestroy(): void {
    // Annuler l'abonnement à l'API pour éviter les fuites de mémoire
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe(); // Désabonnement pour éviter les fuites de mémoire
    }
  }

  // Méthode pour envoyer un message lié à l'article
  postMessage(id: number) {
    // Effectuer une requête POST pour envoyer le message au serveur
    this.http.post(`/api/articles/${id}/messages`, this.formData).subscribe(
      (response) => {
        // Sur succès, rediriger l'utilisateur vers la page de l'article
        this.router.navigate([`/article/${id}`]); // Navigation vers la page de l'article avec l'ID spécifié
        location.reload(); // Recharger la page pour afficher le nouveau message
      },
      (error) => {
        // En cas d'erreur, afficher un message d'erreur
        this.snackbarService.openSnackBar(
          "Erreur lors de l'inscription :",
          'fermer'
        );
      }
    );
  }

  // Méthode pour récupérer les détails d'un article par son ID
  fetchArticle(id: number) {
    // Effectuer une requête GET pour obtenir l'article
    this.articleSubscription = this.http
      .get<Article>(`/api/articles/${id}`)
      .subscribe(
        (response) => {
          this.article = response; // Assigner la réponse à la propriété article
        },
        (error) => {
          // Gérer les erreurs lors de la récupération
          console.error('Erreur lors de la récupération des articles :', error);
          this.snackbarService.openSnackBar(
            'Erreur lors de la récupération des articles :',
            'fermer'
          );
        }
      );
  }

  // Méthode pour revenir à la liste des articles
  goBack(): void {
    this.router.navigate(['/article']); // Rediriger vers la liste des articles
  }
}
