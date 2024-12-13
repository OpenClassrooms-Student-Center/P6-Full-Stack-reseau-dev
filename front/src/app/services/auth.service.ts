// Importation des modules nécessaires depuis Angular
import { Injectable } from '@angular/core'; // Injectable pour créer des services
import { HttpClient, HttpHeaders } from '@angular/common/http'; // HttpClient pour les requêtes HTTP, HttpHeaders pour les en-têtes
import { Observable, map } from 'rxjs'; // Importation de l'Observable et de l'opérateur map

// Déclaration du service AuthService
@Injectable({
  providedIn: 'root', // Indique que ce service est fourni à l'ensemble de l'application
})
export class AuthService {
  private apiUrl = '/api/auth'; // URL de base pour l'API d'authentification

  // Injection d'HttpClient via le constructeur pour effectuer des requêtes HTTP
  constructor(private http: HttpClient) {}

  // Méthode pour effectuer une requête de login à l'API
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http
      .post<any>(`${this.apiUrl}/login`, credentials) // Effectue une requête POST pour le login
      .pipe(
        map((response) => {
          // Transformation de la réponse
          if (response && response.token) {
            this.setToken(response.token); // Si le token est présent, l'enregistrer
          }
          return response; // Retourne la réponse
        })
      );
  }

  // Méthode pour définir (stocker) le token JWT
  private setToken(token: string | null): void {
    if (token) {
      localStorage.setItem('token', token); // Stocke le token dans localStorage
    } else {
      localStorage.removeItem('token'); // Retire le token si nul
    }
  }

  // Méthode pour récupérer le token stocké
  getToken(): string | null {
    return localStorage.getItem('token'); // Retourne le token actuel ou null si non défini
  }

  // Méthode pour déconnecter l'utilisateur
  logout(): void {
    this.setToken(null); // Utilise la méthode setToken pour gérer le token
  }

  // Méthode pour vérifier si l'utilisateur est connecté
  isLoggedIn(): boolean {
    return !!this.getToken(); // Renvoie true si un token est présent, false sinon
  }

  // Méthode pour obtenir les en-têtes HTTP, avec le token si présent
  getHeaders(): HttpHeaders {
    const headersConfig: { [key: string]: string } = {
      'Content-Type': 'application/json', // Définit le type de contenu
      Accept: 'application/json', // Définit le type de réponse acceptée
    };

    const token = this.getToken(); // Récupère le token actuel
    if (token) {
      headersConfig['Authorization'] = `Bearer ${token}`; // Ajoute le token aux en-têtes si présent
    }

    return new HttpHeaders(headersConfig); // Retourne les en-têtes configurés
  }
}
