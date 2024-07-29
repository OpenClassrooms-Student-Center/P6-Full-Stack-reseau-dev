import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";
import {Comment} from "../interfaces/comment.interface";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private pathService = `${environment.baseUrl}/comments`;

  constructor(private httpClient: HttpClient) { }

  /**
   * Create a comment
   * @param postId
   * @param comment
   */
  public create(postId:number, comment: Comment): Observable<Comment[]> {
    return this.httpClient.post<Comment[]>(`${this.pathService}/post/${postId}`, comment);
  }

}
