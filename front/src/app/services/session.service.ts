import {inject, Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Router} from "@angular/router";
import {TokenModel} from "../models/TokenModel";

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  router:Router = inject(Router)
  private tokenKey = 'tokenModel';

  public isLogged: boolean = false;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }
  /**
   * Stores the provided token model in local storage to maintain the session.
   *
   * @param tokenModel The model containing the token information to be saved.
   */
  setSession(tokenModel: TokenModel): void {
    localStorage.setItem(this.tokenKey, JSON.stringify(tokenModel));
  }
  /**
   * Retrieves the token model from local storage.
   *
   * @return The token model if present, or null if not found.
   */
  getSession(): TokenModel | null {
    const tokenJson = localStorage.getItem(this.tokenKey);
    if (tokenJson) {
      return JSON.parse(tokenJson);
    }
    return null;
  }
  /**
   * Checks if the user is authenticated by verifying if a session exists.
   *
   * @return true if the user is authenticated, false otherwise.
   */
  isAuthenticated(): boolean {
    if(this.getSession() != null){
      return true
    }else{
      return false;
    }
  }
  /**
   * Initializes the user session by checking for an existing token in local storage.
   * If a token exists, it restores the session and sets the authentication status.
   */
  initSession() {
    const tokenJson = localStorage.getItem(this.tokenKey);
    if (tokenJson) {
      const tokenModel = JSON.parse(tokenJson);
      this.setSession(tokenModel);
      this.isLogged = true;
      this.next();
    } else {
      this.isLogged = false;
      this.next();
    }
  }
  /**
   * Logs in the user by storing the token in local storage and navigating to the articles page.
   *
   * @param token The token model containing authentication information.
   */
  public logIn(token: TokenModel): void {
    if(token){
      localStorage.setItem('tokenModel', JSON.stringify(token));
      this.isLogged = true;
      this.router.navigate(['/articles'])
      this.next();
    }
  }
  /**
   * Logs out the user by removing the token from local storage and navigating to the login page.
   */
  public logOut(): void {
    localStorage.removeItem('tokenModel');
    // this.user = undefined;
    this.isLogged = false;
    this.router.navigate(['/login'])
    this.next();
  }
  /**
   * Updates the subject tracking the user's logged-in status.
   */
  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
