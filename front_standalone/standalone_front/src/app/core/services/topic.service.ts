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
    return this.apiService.get('/topics');
  }

  getTopic(id: number): Observable<Topic> {
    return this.apiService.get(`/topics/${id}`);
  }

  createTopic(topic: Topic): Observable<Topic> {
    return this.apiService.put('/create', topic);
  }

  updateTopic(topic: Topic): Observable<Topic> {
    return this.apiService.post('/update', topic);
  }

  deleteTopic(id: number): Observable<string> {
    return this.apiService.delete(`/topics/${id}`);
  }
}
