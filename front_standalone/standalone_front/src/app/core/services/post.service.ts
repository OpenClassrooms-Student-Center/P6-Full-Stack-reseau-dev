import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import {NewPostRequestBody, Post, PostToDisplay} from '../models/post';
import {MessageResponse} from "../models/messages";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private apiService: ApiService) { }

  getPosts(): Observable<Post[]> {
    return this.apiService.get('/posts/post/posts');
  }

  getPostById(id: number): Observable<Post> {
    return this.apiService.get(`/posts/post/${id}`);
  }

  createPost(post: Post): Observable<Post> {
    return this.apiService.post('/posts/post/create', post);
  }

  updatePost(post: Post): Observable<Post> {
    return this.apiService.put('/posts/post/update', post);
  }

  deletePost(id: number): Observable<string> {
    return this.apiService.delete(`/posts/post/${id}`);
  }

  newPost(newPost: NewPostRequestBody): Observable<MessageResponse> {
    return this.apiService.post('/posts/post/newpost', newPost);
  }

  getAllPosts(): Observable<PostToDisplay[]> {
    return  this.apiService.get(`/posts/post/allposts`)
  }

  getAllSubscribedTopicPosts(): Observable<PostToDisplay[]> {
    return  this.apiService.get(`/posts/post/subscribedtopicposts`)
  }
}
