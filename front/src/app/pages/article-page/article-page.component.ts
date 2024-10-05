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
  articles: ArticlePage[] = [];
  private articlesSubscription: Subscription | undefined;
  sortAscending: boolean = true; // État du tri (true = A à Z, false = Z à A)

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.fetchArticles(); 
  }

  ngOnDestroy(): void {
    if (this.articlesSubscription) {
      this.articlesSubscription.unsubscribe();
    }
  }

  redirectToCreateArticle(): void {
    this.router.navigate(['/article/add']);
  }

  fetchArticles(): void {
    this.articlesSubscription = this.http.get<ArticlePage[]>('/api/articles')
      .subscribe(
        (response) => {
          console.log(response);  // Ajoutez ceci pour voir la réponse dans la console
          this.articles = response;
          this.sortArticles(); // Trier les articles à l'initialisation
        },
        (error) => {
          console.error('Erreur lors de la récupération des articles :', error);
        }
      );
}


  redirectToArticleDetail(id: number): void {
    this.router.navigate(['/article', id]);
  }

  toggleSort(): void {
    this.sortAscending = !this.sortAscending; // Alterner l'état du tri
    this.sortArticles(); // Trier les articles après avoir changé l'état
  }

  sortArticles(): void {
    if (this.sortAscending) {
      this.articles.sort((a, b) => a.title.localeCompare(b.title)); 
    } else {
      this.articles.sort((a, b) => b.title.localeCompare(a.title));
    }
  }
}
