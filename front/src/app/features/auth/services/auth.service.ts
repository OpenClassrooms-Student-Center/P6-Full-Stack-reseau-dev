import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from "../../../../environments/environment";
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {LoginRequest} from "../interfaces/loginRequest.interface";
import {SessionInformation} from "../interfaces/sessionInformation.interface";
import {Response} from "../../../interfaces/response.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = `${environment.baseUrl}/auth`;

  constructor(private httpClient: HttpClient) { }

  public isLogged(){
    return this.getToken() !== null;
  }

  public getToken(): string | null {
    return sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user') as string).token : null;
  }

  public logIn(sessionInformation: SessionInformation): void {
    sessionStorage.setItem('user', JSON.stringify(sessionInformation));
  }

  public logOut(): void {
    sessionStorage.removeItem('user');
  }

  public register(registerRequest: RegisterRequest): Observable<Response> {
    return this.httpClient.post<Response>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }

}
