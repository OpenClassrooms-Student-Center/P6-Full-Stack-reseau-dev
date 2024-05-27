import {Injectable} from '@angular/core';
import {AuthUser} from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class JWTService {

  jwtToken: string ="";
  constructor() {}


  decodeToken(token: string) {
    return JSON.parse(atob(token.split('.')[1]));
  }

  getBearerToken() {
    const token = this.jwtToken;
    return token ? `Bearer ${token}` : undefined;
  }

  saveToken(token: string){
    console.log('saved token : ', token)
    this.jwtToken = token
  }

  getToken(){
    return this.jwtToken;
  }

  deleteToken(){
    this.jwtToken = "";
  }

  saveUser(token: string): AuthUser | null {
    if (token) {
      this.saveToken(token);
      const decoded = this.decodeToken(token);
      return this.getUserFromToken();
    }
    throw Error('no token at createSession');
  }

  getUserFromToken(): AuthUser | null {
    const token = this.getToken();
    if (token) {
      const decoded = this.decodeToken(token);
      console.log("Decode : ", decoded );
      return {
        role: decoded.scope as string,
        username: decoded.sub as string,
        exp: decoded.exp,
      };
    }
    return null;
  }

  hasValidToken() {
    console.log('checkValid : ', this.getToken() !== "" && this.getToken())
    return this.getToken() !== "" && this.getToken();
  }
}
