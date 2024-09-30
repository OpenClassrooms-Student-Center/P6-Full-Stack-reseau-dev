// Importation des modules nécessaires depuis Angular
import { Injectable } from '@angular/core';
import {
  HttpInterceptor,    // Interface permettant d'intercepter les requêtes HTTP
  HttpRequest,        // Représente une requête HTTP
  HttpHandler,        // Gère la suite du traitement de la requête après interception
  HttpEvent           // Représente un événement HTTP (comme une réponse)
} from '@angular/common/http';
import { Observable } from 'rxjs'; // Observable, pour gérer les flux de données asynchrones
import { AuthService } from '../services/auth.service'; // Service d'authentification

@Injectable() // Indique que cette classe peut être injectée en tant que service
export class AuthInterceptor implements HttpInterceptor {
  // Injection du service d'authentification dans le constructeur
  constructor(private authService: AuthService) {}

  // La méthode intercept intercepte chaque requête HTTP sortante
  intercept(
    req: HttpRequest<any>,  // Requête HTTP originale
    next: HttpHandler       // Gestionnaire pour passer la requête au prochain intercepteur ou au backend
  ): Observable<HttpEvent<any>> {
    // Récupération des en-têtes d'authentification via le service d'authentification
    const headers = this.authService.getHeaders();

    // Clonage de la requête originale avec les nouveaux en-têtes ajoutés
    const authReq = req.clone({ headers });

    // Passage de la requête clonée avec les en-têtes au gestionnaire suivant
    return next.handle(authReq);
  }
}
