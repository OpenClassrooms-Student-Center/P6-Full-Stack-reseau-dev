import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private baseUrl = environment.baseUrl;

  private apiUrl = this.baseUrl+'topic';

  constructor(private http: HttpClient) {}

  // Obtenir le token Bearer
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Remplacez par la méthode d'accès au token
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  // Liste des topics (GET)
  getTopics(): Observable<Topic[]> {
    //return this.http.get<Topic[]>(this.apiUrl, { headers: this.getHeaders() });
    return this.http.get<Topic[]>(this.apiUrl, { headers: this.getHeaders() });

  }
    // Liste des topics (GET)
    getSubscribedTopics(): Observable<Topic[]> {
      return this.http.get<Topic[]>(this.baseUrl+'subscription', { headers: this.getHeaders() });
  
    }

  // Abonnement à un topic
  subscribeUserToTopic(idTopic: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}subscription/${idTopic}`, {}, { headers: this.getHeaders() });
  }

    // Désabonnement à un topic
    unsubscribeUserToTopic(idTopic: number): Observable<void> {
      return this.http.delete<void>(`${this.baseUrl}subscription/${idTopic}`, { headers: this.getHeaders() });
    }
  
}
