import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { PostComment } from "../interfaces/comment.interface";

@Injectable({
    providedIn: 'root'
  })
  export class CommentService{


 


    private baseUrl = environment.baseUrl;

    private apiUrl = this.baseUrl+'comment';
  
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
    getComments(postId:number): Observable<PostComment[]> {
      return this.http.get<PostComment[]>(`${this.apiUrl}/${postId}`, { headers: this.getHeaders() });
  
    }

 

      // Création d'un comment
  createComment(comment:PostComment): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}`, comment, { headers: this.getHeaders() });
  }



  }