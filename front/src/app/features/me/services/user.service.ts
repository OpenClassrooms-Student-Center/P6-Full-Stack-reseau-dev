import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {User} from "../interfaces/user.interface";
import {environment} from "../../../../environments/environment";
import {SessionInformation} from "../../auth/interfaces/sessionInformation.interface";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = `${environment.baseUrl}/user`;

  constructor(private httpClient: HttpClient) { }

  /**
   * Get the user information
   */
  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  /**
   * Update the user information
   * @param user
   */
  public updateInfo(user: User): Observable<SessionInformation> {
    return this.httpClient.put<SessionInformation>(`${this.pathService}/me`, user);
  }


}
