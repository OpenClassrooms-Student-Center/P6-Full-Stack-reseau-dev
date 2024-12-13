// Importation des modules nécessaires depuis Angular
import { Injectable } from '@angular/core';
import {
  HttpInterceptor, // Interface permettant d'intercepter les requêtes HTTP
  HttpRequest, // Représente une requête HTTP
  HttpHandler, // Gère la suite du traitement de la requête après interception
  HttpEvent,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs'; // Importation d'Observable et throwError de la bibliothèque rxjs
import { catchError } from 'rxjs/operators'; // Opérateur pour gérer les erreurs dans les Observables
import { Router } from '@angular/router'; // Pour la navigation entre les routes de l'application
import { AuthService } from '../services/auth.service'; // Service d'authentification

@Injectable() // Indique que cette classe peut être injectée en tant que service
export class AuthInterceptor implements HttpInterceptor {
  // Injection du service d'authentification et du routeur dans le constructeur
  constructor(private authService: AuthService, private router: Router) {}

  // La méthode intercept intercepte chaque requête HTTP sortante
  intercept(
    req: HttpRequest<any>, // Requête HTTP originale
    next: HttpHandler // Gestionnaire pour passer la requête au prochain intercepteur ou au backend
  ): Observable<HttpEvent<any>> {
    // Récupération des en-têtes d'authentification via le service d'authentification
    const headers = this.authService.getHeaders();

    // Clonage de la requête originale avec les nouveaux en-têtes ajoutés
    const authReq = req.clone({ headers });

    // Passage de la requête clonée avec les en-têtes au gestionnaire suivant
    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        // Vérification si l'erreur est une erreur 401 (non autorisé)
        if (error.status === 401) {
          // Redirection vers la page de connexion si l'utilisateur n'est pas autorisé
          this.router.navigateByUrl('/login');
        }
        // Propagation de l'erreur pour un traitement ultérieur
        return throwError(error);
      })
    );
  }
}
