// Importation des dépendances nécessaires
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ArticlePage } from 'src/app/interfaces/article.interface';
import { Subscription } from 'rxjs';

// Déclaration du composant
@Component({
  selector: 'app-article-page',
  templateUrl: './article-page.component.html',
  styleUrls: ['./article-page.component.scss'],
})

// Définition de la classe ArticlePageComponent qui implémente les interfaces OnInit et OnDestroy
export class ArticlePageComponent implements OnInit, OnDestroy {
  articles: ArticlePage[] = []; // Déclaration d'un tableau pour stocker les articles récupérés
  private articlesSubscription: Subscription | undefined; // Variable pour gérer l'abonnement à l'API des articles
  sortAscending: boolean = true; // État du tri, initialisé à vrai pour le tri ascendant

  // Constructeur qui injecte les services HttpClient et Router
  constructor(private http: HttpClient, private router: Router) {}

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    this.fetchArticles(); // Appel de la méthode pour récupérer les articles
  }

  // Méthode appelée avant la destruction du composant
  ngOnDestroy(): void {
    if (this.articlesSubscription) {
      this.articlesSubscription.unsubscribe(); // Désabonnement pour éviter les fuites de mémoire
    }
  }

  // Méthode pour rediriger l'utilisateur vers la page de création d'article
  redirectToCreateArticle(): void {
    this.router.navigate(['/article/add']); // Navigation vers la route de création d'article
  }

  // Méthode pour récupérer les articles depuis l'API
  fetchArticles(): void {
    this.articlesSubscription = this.http
      .get<ArticlePage[]>('/api/articles') // Requête GET vers l'API
      .subscribe(
        (response) => {
          console.log(response); // Affichage de la réponse dans la console pour débogage
          this.articles = response; // Stockage des articles récupérés dans la propriété `articles`
          this.sortArticles(); // Appel de la méthode pour trier les articles après récupération
        },
        (error) => {
          console.error('Erreur lors de la récupération des articles :', error); // Gestion des erreurs de requête
        }
      );
  }

  // Méthode pour rediriger vers la page de détails d'un article spécifique
  redirectToArticleDetail(id: number): void {
    this.router.navigate(['/article', id]); // Navigation vers la route de détails de l'article avec l'ID
  }

  // Méthode pour basculer l'état du tri entre ascendant et descendant
  toggleSort(): void {
    this.sortAscending = !this.sortAscending; // Inversion de l'état de tri
    this.sortArticles(); // Appel de la méthode pour trier les articles selon le nouvel état
  }

  // Méthode pour trier les articles par titre
  sortArticles(): void {
    if (this.sortAscending) {
      this.articles.sort((a, b) => a.title.localeCompare(b.title)); // Tri ascendant des articles par titre
    } else {
      this.articles.sort((a, b) => b.title.localeCompare(a.title)); // Tri descendant des articles par titre
    }
  }
}
