import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {Token} from "../interfaces/token.interface";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

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

}
