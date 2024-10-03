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
  
  // Tableau pour stocker les indices des articles à afficher, potentiellement utilisé pour la pagination ou le rendu
  indexArray: number[] = [];

  // Constructeur du composant qui injecte les services HttpClient et Router
  constructor(private http: HttpClient, private router: Router) {}

  // Méthode appelée une fois que le composant est initialisé (cycle de vie d'Angular)
  ngOnInit(): void {
    this.fetchArticles(); // Appel de la méthode pour récupérer les articles à l'initialisation
  }
    
  // Méthode pour rediriger l'utilisateur vers la page de création d'un article
  redirectToCreateArticle() {
    this.router.navigate(['/article/add']); // Utilise le service Router pour naviguer vers la route spécifiée
  }
  redirectToArticleDetail(id: number): void {
    this.router.navigate(['/article', id]);
  }

  // Méthode pour récupérer les articles depuis une API (via une requête HTTP GET)
  fetchArticles(): void {
    // Requête HTTP GET pour obtenir la liste des articles depuis l'API à l'URL spécifiée
    this.http.get<any>('http://localhost:8080/api/articles')
      .subscribe((response) => { 
        // En cas de succès, on récupère les articles depuis la réponse et on les stocke dans la propriété articles
        this.articles = response.articles;

        // Remplissage de indexArray avec des indices basés sur la longueur de la liste des articles (divisée par 2 ici)
        for (let i = 0; i < this.articles.length / 2; i++) {
          this.indexArray.push(i); // Ajoute chaque indice calculé dans indexArray
        }
      }, (error) => {
        // En cas d'erreur lors de la requête HTTP, un message d'erreur est affiché dans la console
        console.error('Erreur lors de la récupération des articles :', error);
      });
  }
}
