// Importation des modules nécessaires depuis Angular
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'; // HttpClient pour les requêtes HTTP, HttpHeaders pour les en-têtes
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root' // Indique que ce service est fourni à l'ensemble de l'application
})
export class AuthService {
  private apiUrl = '/api/auth';
  
  // Injection d'HttpClient via le constructeur pour effectuer des requêtes HTTP
  constructor(private http: HttpClient) {}

  // Méthode pour effectuer une requête de login à l'API
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials)
      .pipe(
        map(response => {
          if (response && response.token) {
            this.setToken(response.token);
          }
          return response;
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
      'Content-Type': 'application/json',
      Accept: 'application/json'
    };

    const token = this.getToken(); // Récupère le token actuel
    if (token) {
      headersConfig['Authorization'] = `Bearer ${token}`;
    }

    return new HttpHeaders(headersConfig);
  }
}
