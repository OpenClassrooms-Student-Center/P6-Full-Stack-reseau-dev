import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Post } from "../interfaces/post.interface";

@Injectable({
    providedIn: 'root'
  })
  export class PostService{


    private baseUrl = environment.baseUrl;

    private apiUrl = this.baseUrl+'post';
  
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
    getPosts(): Observable<Post[]> {
      return this.http.get<Post[]>(this.apiUrl, { headers: this.getHeaders() });
  
    }

    detail(id: string): Observable<Post> {
      return this.http.get<Post>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
      }

            // Création d'un comment
        createPost(post:Post): Observable<void> {
          return this.http.post<void>(`${this.apiUrl}`, post, { headers: this.getHeaders() });
        }
      

  }