import {Injectable} from '@angular/core';
import {AuthUser} from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class JWTService {

  jwtToken?: string | null;
  constructor() {}


  decodeToken(token: string) {
    return JSON.parse(atob(token.split('.')[1]));
  }

  getBearerToken() {
    const token = this.jwtToken;
    return token ? `Bearer ${token}` : undefined;
  }

  saveToken(token: string){
    this.jwtToken = token
  }

  getToken(){
    return this.jwtToken;
  }

  deleteToken(){
    this.jwtToken = null;
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
    const authUser = this.getUserFromToken();
    if (authUser && authUser.role && authUser.username && authUser.exp) {
      return Date.now() <= authUser.exp * 1000;
    }
    return false;
  }
}
