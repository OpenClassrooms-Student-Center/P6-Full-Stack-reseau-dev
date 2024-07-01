import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public get isLogged(): boolean {
    return this.getToken() !== null;
  }

  public logIn(user: SessionInformation): void {
    localStorage.setItem('token', user.token);
    this.sessionInformation = user;
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.sessionInformation = undefined;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  private getToken(): string | null {
    return localStorage.getItem('token');
  }

}
