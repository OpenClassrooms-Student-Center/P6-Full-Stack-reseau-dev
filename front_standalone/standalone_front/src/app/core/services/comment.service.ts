import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Comment, CommentToDisplay} from '../models/comment';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiUrl:string = '/comments/comment';

  constructor(private http: HttpClient, private apiService: ApiService) { }

  getComments(): Observable<Comment[]> {
    return this.apiService.get(this.apiUrl + '/comments');
  }

  getCommentById(id: number): Observable<Comment> {
    return this.apiService.get(this.apiUrl + `/${id}`);
  }

  createComment(comment: Comment): Observable<Comment> {
    return this.apiService.post(this.apiUrl + '/create', comment);
  }

  updateComment(comment: Comment): Observable<Comment> {
    return this.apiService.put(this.apiUrl + '/update', comment);
  }

  deleteComment(id: number): Observable<string> {
    return this.apiService.delete(this.apiUrl + `/${id}`);
  }

  commentByPost(postId: number): Observable<CommentToDisplay[]> {
    return this.apiService.get(this.apiUrl + `/bypost/${postId}`);
  }
}
