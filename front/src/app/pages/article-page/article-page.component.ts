import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { Router } from '@angular/router'; 
import { ArticlePage } from 'src/app/interfaces/article.interface';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-article-page',
  templateUrl: './article-page.component.html',
  styleUrls: ['./article-page.component.scss']
})
export class ArticlePageComponent implements OnInit, OnDestroy {
  articles: ArticlePage[] = [];  // Tableau d'articles récupérés
  private articlesSubscription: Subscription | undefined;  // Pour gérer l'abonnement
  sortAscending: boolean = true;  // État du tri

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.fetchArticles();  // Récupérer les articles à l'initialisation
  }

  ngOnDestroy(): void {
    // Annuler l'abonnement à l'API lorsqu'on quitte la page
    if (this.articlesSubscription) {
      this.articlesSubscription.unsubscribe();
    }
  }

  redirectToCreateArticle(): void {
    // Redirection vers la page de création d'article
    this.router.navigate(['/article/add']);
  }

  fetchArticles(): void {
    // Récupérer les articles depuis l'API
    this.articlesSubscription = this.http.get<ArticlePage[]>('/api/articles')
      .subscribe(
        (response) => {
          console.log(response);  // Voir la réponse dans la console pour déboguer
          this.articles = response;  // Stocker les articles dans la propriété `articles`
          this.sortArticles();  // Trier les articles après les avoir récupérés
        },
        (error) => {
          console.error('Erreur lors de la récupération des articles :', error);
        }
      );
  }

  redirectToArticleDetail(id: number): void {
    // Redirection vers la page de détails d'un article
    this.router.navigate(['/article', id]);
  }

  toggleSort(): void {
    // Alterner le tri (ascendant ou descendant)
    this.sortAscending = !this.sortAscending;
    this.sortArticles();  // Réordonner les articles après avoir modifié l'état de tri
  }

  sortArticles(): void {
    // Tri des articles par titre (A-Z ou Z-A selon `sortAscending`)
    if (this.sortAscending) {
      this.articles.sort((a, b) => a.title.localeCompare(b.title));
    } else {
      this.articles.sort((a, b) => b.title.localeCompare(a.title));
    }
  }
}
