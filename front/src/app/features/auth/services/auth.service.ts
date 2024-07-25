import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, map, Observable} from 'rxjs';
import {environment} from "../../../../environments/environment";
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {LoginRequest} from "../interfaces/loginRequest.interface";
import {SessionInformation} from "../interfaces/sessionInformation.interface";
import {Response} from "../../../interfaces/response.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private sessionInformationSubject = new BehaviorSubject<SessionInformation | null>(null);
  private pathService = `${environment.baseUrl}/auth`;

  public isLogged:boolean = false;

  constructor(private httpClient: HttpClient) { }

  public $isLogged(): Observable<boolean> {
    return this.sessionInformationSubject.asObservable().pipe(
      map((sessionInformation: SessionInformation | null) => {
        return sessionInformation !== null;
      })
    );
  }

  public getToken(): string | null {
    return this.sessionInformationSubject.getValue()?.token ?? null;
  }

  public logIn(sessionInformation: SessionInformation): void {
    this.isLogged = true;
    this.sessionInformationSubject.next(sessionInformation);
  }

  public logOut(): void {
    this.isLogged = false;
    this.sessionInformationSubject.next(null);
  }

  public register(registerRequest: RegisterRequest): Observable<Response> {
    return this.httpClient.post<Response>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }

}
