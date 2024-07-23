import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {environment} from "../../../../environments/environment";
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {LoginRequest} from "../interfaces/loginRequest.interface";
import {Token} from "../interfaces/token.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  private pathService = `${environment.baseUrl}/auth`;

  constructor(private httpClient: HttpClient) { }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public get isLogged(): boolean {
    return this.getToken() !== null;
  }

  public logIn(token: Token): void {
    this.setToken(token.token)
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  private getToken(): string | null {
    return localStorage.getItem('token');
  }

  private setToken(token: string): void {
    return localStorage.setItem('token',token);
  }

  public register(registerRequest: RegisterRequest): Observable<Token> {
    return this.httpClient.post<Token>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<Token> {
    return this.httpClient.post<Token>(`${this.pathService}/login`, loginRequest);
  }

}
