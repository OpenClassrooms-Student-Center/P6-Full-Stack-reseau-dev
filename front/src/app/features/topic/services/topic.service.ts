import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";
import {Topic} from "../interfaces/topic.interface";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = `${environment.baseUrl}/topics`;

  constructor(private httpClient: HttpClient) { }

  /**
   * Get the topics that the user is not following
   */
  public getTopicsNotFollowedByUser(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }

  /**
   * Get all the topics
   */
  public findAll(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/all`);
  }

  /**
   * Get the topics that the user is following
   */
  public getUserSubscriptions(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/user`);
  }

  /**
   * Follow a topic
   * @param id
   */
  public follow(id:number):Observable<Topic[]>{
    return this.httpClient.post<Topic[]>(`${this.pathService}/${id}/subscribe`, {});
  }

  /**
   * Unfollow a topic
   * @param id
   */
  public unfollow(id:number):Observable<Topic[]>{
    return this.httpClient.delete<Topic[]>(`${this.pathService}/${id}/subscribe`, {});
  }

}
