// Importation des modules nécessaires d'Angular
import { Component, OnInit } from '@angular/core'; // Composant et cycle de vie
import { HttpClient } from '@angular/common/http'; // Service HttpClient pour faire des requêtes HTTP

// Définition du décorateur @Component pour ce composant
@Component({
  selector: 'app-article-page', // Nom du sélecteur pour utiliser ce composant dans le HTML
  templateUrl: './article-page.component.html', // Chemin vers le template HTML du composant
  styleUrls: ['./article-page.component.scss'] // Chemin vers les styles SCSS du composant
})
// Classe du composant ArticlePageComponent
export class ArticlePageComponent implements OnInit {
  // Tableau pour stocker les articles récupérés depuis l'API
  articles: any[] = [];
  // Tableau pour stocker les indices des articles à afficher (utilisé pour la pagination ou le rendu)
  indexArray: number[] = [];

  // Constructeur qui injecte le service HttpClient
  constructor(private http: HttpClient) { }

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    this.fetchArticles(); // Appel de la méthode pour récupérer les articles
  }

  // Méthode pour récupérer les articles depuis l'API
  fetchArticles(): void {
    // Requête GET vers l'API pour récupérer les articles
    this.http.get<any>('http://localhost:8080/api/articles')
      .subscribe((response) => {
        // On assigne les articles récupérés à la propriété articles
        this.articles = response.articles;

        // Remplit indexArray avec les indices pour afficher les articles
        for (let i = 0; i < this.articles.length / 2; i++) {
          this.indexArray.push(i); // Ajoute chaque indice à indexArray
        }
      }, (error) => {
        // Gestion des erreurs en cas d'échec de la récupération des articles
        console.error('Erreur lors de la récupération des articles :', error);
      });
  }
}
