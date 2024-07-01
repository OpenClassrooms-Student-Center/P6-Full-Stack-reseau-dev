import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import {Token} from "../../../interfaces/token.interface";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = `${environment.baseUrl}/auth`;

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<Token> {
    return this.httpClient.post<Token>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<Token> {
    return this.httpClient.post<Token>(`${this.pathService}/login`, loginRequest);
  }

}
