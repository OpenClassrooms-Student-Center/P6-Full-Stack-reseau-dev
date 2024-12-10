import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {UserLoginModel} from "../models/UserLoginModel";
import {Observable} from "rxjs";
import {UserRegisterModel} from "../models/UserRegisterModel";
import {UserEmailModel} from "../models/UserEmailModel";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http: HttpClient = inject(HttpClient);
  prefix: string = "/auth/";
  /**
   * Sends a login request to the server with the user's login details.
   *
   * @param userLogin The model containing the user's login credentials.
   * @return An Observable containing the server's response.
   */
  login(userLogin:UserLoginModel):Observable<any>{
    return this.http.post<UserLoginModel>(environment.developpement + this.prefix +'login',userLogin);
  }
  /**
   * Sends a registration request to the server with the user's registration details.
   *
   * @param userRegister The model containing the user's registration information.
   * @return An Observable containing the server's response.
   */
  register(userRegister:UserRegisterModel):Observable<any>{
    return this.http.post<UserRegisterModel>(environment.developpement + this.prefix + 'register',userRegister);
  }
  /**
   * Retrieves the profile of the currently authenticated user.
   *
   * @return An Observable containing the user's profile data.
   */
  profil():Observable<any>{
      return this.http.get(environment.developpement + this.prefix +'profil');
  }
  /**
   * Updates the authenticated user's profile with the provided email information.
   *
   * @param email The model containing the user's updated email information.
   * @return An Observable containing the server's response.
   */
  editProfil(email:UserEmailModel):Observable<any>{
    return this.http.put<UserEmailModel>(environment.developpement + this.prefix + 'profil', email);
  }
  /**
   * Retrieves the list of themes the authenticated user is subscribed to.
   *
   * @return An Observable containing the list of the user's subscribed themes.
   */
  getUserThemes(): Observable<any> {
    return this.http.get(`${environment.developpement + this.prefix}themes`);
  }
}
