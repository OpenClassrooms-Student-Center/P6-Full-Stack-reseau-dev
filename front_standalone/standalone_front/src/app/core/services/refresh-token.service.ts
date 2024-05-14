import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RefreshTokenService {

  constructor( ) {
    this.rememberMe = localStorage.getItem(this.rememberMe_key) ? true : false;
  }

  token_key = 'mdd_refresh_token_key';
  rememberMe_key = 'mdd_rememberMe_key';
  private rememberMe: boolean = false;

  saveRefreshToken(token: string) {
    localStorage.setItem(this.token_key, token);
  }

  getRefreshToken() {
    return localStorage.getItem(this.token_key);
  }

  deleteRefreshToken() {
    return localStorage.removeItem(this.token_key);
  }

  activateRememberMe(){
    this.rememberMe = true;
    localStorage.setItem(this.rememberMe_key, "1");
  }

  deactivateRememberMe() {
    this.rememberMe = false;
    localStorage.removeItem(this.rememberMe_key);
  }

}
