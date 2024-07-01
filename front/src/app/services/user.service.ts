import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import {Token} from "../interfaces/token.interface";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = `${environment.baseUrl}/user`;

  constructor(private httpClient: HttpClient) { }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  public updateInfo(user: User): Observable<Token> {
    return this.httpClient.put<Token>(`${this.pathService}/me`, user);
  }


}
