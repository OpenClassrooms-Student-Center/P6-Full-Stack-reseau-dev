import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Article } from '../interface/article';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private pathService = 'api/article';

  constructor(private httpClient : HttpClient) { 
  }

  public create(article : Article): Observable<Article> {
    return this.httpClient.post<Article>(`${this.pathService}`, article);
  }
}
