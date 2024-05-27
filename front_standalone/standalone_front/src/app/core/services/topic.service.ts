import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../models/topic';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private apiService: ApiService) { }

  getTopics(): Observable<Topic[]> {
    return this.apiService.get('/topic/topics');
  }

  getTopic(id: number): Observable<Topic> {
    return this.apiService.get(`/topic/${id}`);
  }

  createTopic(topic: Topic): Observable<Topic> {
    return this.apiService.post('/topic/create', topic);
  }

  updateTopic(topic: Topic): Observable<Topic> {
    return this.apiService.put('/topic/update', topic);
  }

  deleteTopic(id: number): Observable<string> {
    return this.apiService.delete(`/topic/${id}`);
  }

  unsubscribeTopic(id: number): Observable<void> {
    return this.apiService.put(`/topic/unsubscribe/${id}`);
  }

  subscribeTopic(id: number): Observable<void> {
    return this.apiService.put(`/topic/subscribe/${id}`);
  }

}
