import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Post} from "../interfaces/post.interface";
import {environment} from "../../../../environments/environment";
import {Response} from "../../../interfaces/response.interface";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private pathService = `${environment.baseUrl}/posts`;

  constructor(private httpClient: HttpClient) { }

  public findAll(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.pathService}`);
  }

  public findById(id:number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  public create(post: Post): Observable<Post> {
    return this.httpClient.post<Post>(this.pathService, post);
  }

}
