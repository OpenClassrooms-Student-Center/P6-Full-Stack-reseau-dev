import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { RegisterRequest, LoginRequest } from '@core/types/auth.type';
import { UserInfo } from '@core/types/user.type';
import { ApiService } from '@core/services/api/api.service';

/**
 * Service for handling user authentication, including registration and login.
 * @Injectable
 * @providedIn: 'root'
 */
@Injectable({
  providedIn: 'root',
})
export class AuthService extends ApiService {
  /**
   * The base pathname for authentication-related API endpoints.
   */
  private readonly API_PATHNAME: string = 'api/auth';

  /**
   * Observable to track the loading state of authentication-related operations.
   */
  public isLoading$ = new BehaviorSubject<boolean>(false);

  /**
   * Observable to track error messages.
   */
  public errorMessage$ = new BehaviorSubject<string>('');

  /**
   * Observable to track if there's an error during any operation.
   */
  public hasError$ = new BehaviorSubject<boolean>(false);

  /**
   * Registers a new user.
   * @param {RegisterRequest} registerRequest - The registration request data.
   * @returns {Observable<UserInfo>} An Observable with the response data.
   */
  public register = (
    registerRequest: RegisterRequest
  ): Observable<UserInfo> => {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/register`,
      registerRequest
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  };

  /**
   * Logs in an existing user.
   * @param {LoginRequest} loginRequest - The login request data.
   * @returns {Observable<UserInfo>} An Observable with the response data.
   */
  public login = (loginRequest: LoginRequest): Observable<UserInfo> => {
    this.isLoading$.next(true);

    return this.fetchPost<UserInfo>(
      `${this.API_PATHNAME}/login`,
      loginRequest
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  };

  /**
   * Updates the loading state.
   * @param {Readonly<UserInfo>} value - The value to set.
   * @private
   */
  private updateLoadingState = (value: Readonly<UserInfo>): void => {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  };

  /**
   * Handles general errors.
   * @param {any} err - The error object.
   * @param {Observable<UserInfo>} caught - The caught Observable.
   * @returns {never} Throws an error.
   * @private
   */
  private handleErrors = (err: any, caught: Observable<UserInfo>): never => {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  };
}
