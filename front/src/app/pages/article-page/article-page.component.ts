// Importation des modules nécessaires d'Angular
import { Component, OnInit } from '@angular/core'; // Permet de définir un composant et de gérer le cycle de vie d'un composant
import { HttpClient } from '@angular/common/http'; // Service HttpClient pour effectuer des requêtes HTTP
import { Router } from '@angular/router'; // Service Router pour naviguer entre les pages

// Définition du décorateur @Component qui spécifie les métadonnées du composant
@Component({
  selector: 'app-article-page', // Nom du sélecteur utilisé pour inclure ce composant dans le HTML
  templateUrl: './article-page.component.html', // Chemin vers le fichier de template HTML associé à ce composant
  styleUrls: ['./article-page.component.scss'] // Chemin vers le fichier de styles SCSS associé à ce composant
})

// Classe du composant ArticlePageComponent
export class ArticlePageComponent implements OnInit {
  // Tableau pour stocker les articles récupérés depuis l'API
  articles: any[] = [];

  // Constructeur du composant qui injecte les services HttpClient et Router
  constructor(private http: HttpClient, private router: Router) {}

  // Méthode appelée une fois que le composant est initialisé (cycle de vie d'Angular)
  ngOnInit(): void {
    this.fetchArticles(); // Appel de la méthode pour récupérer les articles à l'initialisation
  }
    
  redirectToCreateArticle(): void {
    this.router.navigate(['/article/add']); // Utilise le service Router pour naviguer vers la route spécifiée
  }


  // Méthode pour récupérer les articles depuis une API (via une requête HTTP GET)
  fetchArticles(): void {
    this.http.get<any[]>('http://localhost:8080/api/articles')
      .subscribe(
        (response) => {
          this.articles = response;
        },
        (error) => {
          console.error('Erreur lors de la récupération des articles :', error);
        }
      );
  }

  redirectToArticleDetail(id: number): void {
    this.router.navigate(['/article', id]);
  }
  sortArticlesAZ(): void {
    this.articles.sort((a, b) => a.title.localeCompare(b.title));
  }
  sortArticlesZA(): void {
    this.articles.sort((a, b) => b.title.localeCompare(a.title));
  }
}
