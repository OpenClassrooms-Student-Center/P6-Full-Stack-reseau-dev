import { Injectable } from '@angular/core';
import {BehaviorSubject, distinctUntilChanged, map, ReplaySubject, tap} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {JWTService} from "./jwt.service";
import {ApiService} from "./api.service";
import {UserInfo} from "../models/userInfo";
import {RefreshTokenService} from "./refresh-token.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<UserInfo>({} as UserInfo);
  public currentUser = this.currentUserSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  private isAuthenticatedSubject = new ReplaySubject<boolean>(1);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  private jwtToken = "";



  constructor(private http: HttpClient,
              private jwt: JWTService,
              private apiService: ApiService,
              private refreshTokenService: RefreshTokenService) {
    this.isLoggedIn();
  }

  login({ username, password }: { username: string; password: string }) {

    return this.http
      .post<any>(`/api/token`, null,
        {
          headers: new HttpHeaders(
            {
              'Authorization': "Basic " + btoa(username + ":" + password)
            } ),
          observe: 'response'
        })
      .pipe(tap((r) => {
        console.log('Body : ', r.body);
        this.createSession(r.body.token);
        this.saveRefreshToken(r.body.refreshToken)
      }));
  }

  refreshToken(){
    return this.http
      .post<any>(`/api/refreshtoken`, {token: this.refreshTokenService.getRefreshToken()},
        {
          headers: new HttpHeaders(
            {
            } ),
          observe: 'response'
        })
      .pipe(tap((r) => {
        console.log("refreshgot:", r.body.token)
        this.refreshSession(r.body.token);
      }));
  }

  register ({username, email, password}: {username : string, email : string, password : string}){
    return this.http
      .post<any>(`/api/register`,
      {
        username : username,
        mail : email,
        password : password,
      })
  }

  /**
   * deletes JWT Token
   * Resets isAuthenticated and CurrentUser observables
   */
  logout() {
    this.jwt.deleteToken();
    this.refreshTokenService.deleteRefreshToken();
    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next({});
  }

  /**
   * saves User token and updates observables currentUserSubject and isAuthenticatedSubject
   s  */
  createSession(token: string | null) {
    if (token == null){return;}
    const authUser: UserInfo | null = this.jwt.saveUser(token);
    if (authUser) {
      this.currentUserSubject.next(authUser);
      this.isAuthenticatedSubject.next(true);
    }
  }


  refreshSession(token: string | null){
    if (token == null){return;}
    this.jwt.saveUser(token);
  }

  saveRefreshToken(refreshToken: string){
    this.refreshTokenService.saveRefreshToken(refreshToken);
  }

  /**
   *
   * @returns Observable<string | undefined> coresponding to current user username
   */
  getUsername() {
    return this.currentUser.pipe(
      map((u) => {
        return u.username || this.jwt.getUserFromToken()?.username;
      })
    );
  }

  /**
   *
   * @returns a boolean to indicate if a token that has not expired is saved in localStorage
   * Real validity check on the token is done server-side
   */
  isLoggedIn() {
    return this.jwt.hasValidToken();
  }
}
