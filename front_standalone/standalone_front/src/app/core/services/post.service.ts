import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Post } from '../models/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private apiService: ApiService) { }

  getPosts(): Observable<Post[]> {
    return this.apiService.get('posts/post/posts');
  }

  getPostById(id: number): Observable<Post> {
    return this.apiService.get(`posts/post/${id}`);
  }

  createPost(post: Post): Observable<Post> {
    return this.apiService.put('posts/post/create', post);
  }

  updatePost(post: Post): Observable<Post> {
    return this.apiService.post('posts/post/update', post);
  }

  deletePost(id: number): Observable<string> {
    return this.apiService.delete(`posts/post/${id}`);
  }

}
