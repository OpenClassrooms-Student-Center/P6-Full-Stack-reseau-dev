import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Post} from "../interfaces/post.interface";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private pathService = `${environment.baseUrl}/posts`;

  constructor(private httpClient: HttpClient) { }

  /**
   * Get all the posts
   */
  public findAll(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.pathService}`);
  }

  /**
   * Find a post by id
   * @param id
   */
  public findById(id:number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  /**
   * Create a post
   * @param post
   */
  public create(post: Post): Observable<Post> {
    return this.httpClient.post<Post>(this.pathService, post);
  }

}
