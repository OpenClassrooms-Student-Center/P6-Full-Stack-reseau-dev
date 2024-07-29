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

  /**
   * Check if the user is logged
   */
  public isLogged(){
    return this.getToken() !== null;
  }

  /**
   * Get the token of the user
   */
  public getToken(): string | null {
    return sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user') as string).token : null;
  }

  /**
   * Save user credentials
   * @param sessionInformation
   */
  public save(sessionInformation: SessionInformation): void {
    sessionStorage.setItem('user', JSON.stringify(sessionInformation));
  }

  /**
   * Log out the user
   */
  public logOut(): void {
    sessionStorage.removeItem('user');
  }

  /**
   * Register a user
   * @param registerRequest
   */
  public register(registerRequest: RegisterRequest): Observable<Response> {
    return this.httpClient.post<Response>(`${this.pathService}/register`, registerRequest);
  }

  /**
   * Log in a user
   * @param loginRequest
   */
  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }

}
