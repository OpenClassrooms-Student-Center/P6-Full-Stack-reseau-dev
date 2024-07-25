import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";
import {Topic} from "../interfaces/topic.interface";
import {Response} from "../../../interfaces/response.interface";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = `${environment.baseUrl}/topics`;

  constructor(private httpClient: HttpClient) { }

  public getTopicsNotFollowedByUser(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }

  public findAll(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/all`);
  }

  public getUserSubscriptions(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/user`);
  }

  public follow(id:number):Observable<Topic[]>{
    return this.httpClient.post<Topic[]>(`${this.pathService}/${id}/subscribe`, {});
  }

  public unfollow(id:number):Observable<Topic[]>{
    return this.httpClient.delete<Topic[]>(`${this.pathService}/${id}/subscribe`, {});
  }

}
