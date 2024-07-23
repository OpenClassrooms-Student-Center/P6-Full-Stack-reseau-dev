import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private pathService = 'http://localhost:3001/api/topic';

  constructor(private httpClient: HttpClient) { }

  public findAll(): Observable<any> {
    return this.httpClient.get<any>(`${this.pathService}`);
  }

  public getUserSubscriptions(): Observable<any> {
    return this.httpClient.get<any>(`${this.pathService}/user`);
  }

}
