import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/session-information.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  constructor() {
    // Initialiser l'état de la session depuis localStorage
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.sessionInformation = JSON.parse(storedUser) as SessionInformation;
      this.isLogged = true;
      this.next();
    }
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    localStorage.setItem('user', JSON.stringify(user));

    this.sessionInformation = user;
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    this.next();
    localStorage.setItem('user', '');
    localStorage.setItem('token', '');


  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
