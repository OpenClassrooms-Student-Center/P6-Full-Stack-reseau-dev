import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { ApiService } from '../api/api.service';
import { UserBasicInfo, UserEntity } from '@core/types/user.type';
import { Message } from '@core/types/message.type';

/**
 * Service responsible for managing user-related operations.
 * @Injectable
 * @providedIn: 'root'
 */
@Injectable({
  providedIn: 'root',
})
export class UserService extends ApiService {
  /**
   * The base pathname for user-related API endpoints.
   */
  private readonly API_PATHNAME: string = 'api/users';
  /**
   * Observable indicating whether data is being loaded.
   */
  public isLoading$ = new BehaviorSubject<boolean>(false);

  /**
   * Observable containing error messages.
   */
  public errorMessage$ = new BehaviorSubject<string>('');

  /**
   * Observable indicating whether an error occurred.
   */
  public hasError$ = new BehaviorSubject<boolean>(false);

  /**
   * Retrieves user information.
   * @returns An Observable containing user entity information.
   */
  public getUser = (): Observable<UserEntity> => {
    this.isLoading$.next(true);

    return this.fetchGet<UserEntity>(this.API_PATHNAME).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  };

  /**
   * Updates user information.
   * @param {Omit<UserBasicInfo, 'id'>} updatedUser - The updated user information.
   * @returns An Observable containing a message indicating the update status.
   */
  public updateUser = (
    updatedUser: Omit<UserBasicInfo, 'id'>
  ): Observable<Message> => {
    this.isLoading$.next(true);

    return this.fetchPut<Message>(this.API_PATHNAME, updatedUser).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  };

  /**
   * Updates the loading state.
   * @param {any} value - The value to update.
   */
  private updateLoadingState = (value: any): void => {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  };

  /**
   * Handles API errors.
   * @param {any} err - The error object.
   * @param {any} caught - The Observable which emitted the error.
   * @returns {never}
   */
  private handleErrors = (err: any, caught: any): never => {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  };
}
