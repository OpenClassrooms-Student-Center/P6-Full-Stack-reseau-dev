// Importation des modules nécessaires depuis Angular
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'; // HttpClient pour les requêtes HTTP, HttpHeaders pour les en-têtes
import { Observable } from 'rxjs'; // Observable pour gérer les réponses HTTP de manière asynchrone

@Injectable({
  providedIn: 'root' // Indique que ce service est fourni à l'ensemble de l'application
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; // URL de base de l'API d'authentification
  private token: string | null = localStorage.getItem('token');

  // Injection d'HttpClient via le constructeur pour effectuer des requêtes HTTP
  constructor(private http: HttpClient) {}

  // Méthode pour effectuer une requête de login à l'API
  // Prend un objet "credentials" contenant l'email et le mot de passe, et renvoie un Observable
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials); // Envoie la requête POST avec les identifiants de connexion
  }

  // Méthode pour définir (stocker) le token JWT
  setToken(token: string | null): void {
    this.token = token; // Le token est stocké localement dans cette propriété
  }

  // Méthode pour récupérer le token stocké
  getToken(): string | null {
    return this.token; // Retourne le token actuel ou null si non défini
  }

  // Méthode pour obtenir les en-têtes HTTP, avec le token si présent
  getHeaders(): HttpHeaders {
    // Configuration de base des en-têtes HTTP
    const headersConfig: { [key: string]: string } = {
      'Content-Type': 'application/json', // Type de contenu JSON
      Accept: 'application/json' // Accepte une réponse JSON
    };

    // Si un token JWT est disponible, on l'ajoute dans l'en-tête Authorization
    if (this.token) {
      headersConfig['Authorization'] = `Bearer ${this.token}`; // Ajoute le token sous le format "Bearer {token}"
    }

    // Crée et retourne les en-têtes configurés
    return new HttpHeaders(headersConfig);
  }
}
