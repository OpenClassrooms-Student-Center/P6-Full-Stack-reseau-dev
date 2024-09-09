import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import {
  BehaviorSubject,
  Observable,
  catchError,
  combineLatest,
  map,
  tap,
} from 'rxjs';
import { Topic, TopicSubscription } from '@core/types/topic.type';
import { Message } from '@core/types/message.type';

/**
 * Service responsible for managing topics and subscriptions.
 * @Injectable
 * @providedIn: 'root'
 */
@Injectable({
  providedIn: 'root',
})
export class TopicService extends ApiService {
  /**
   * The base pathname for topic-related API endpoints.
   */
  private readonly API_PATHNAME: string = 'api/themes';

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
   * Retrieves all topics.
   * @returns An Observable containing an array of topics.
   */
  public getAllThemes = (): Observable<Topic[]> => {
    this.isLoading$.next(true);

    return this.fetchGet<Topic[]>(this.API_PATHNAME).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  };

  /**
   * Retrieves all subscribed topics.
   * @returns An Observable containing an array of topic subscriptions.
   */
  public getAllSubscribedThemes = (): Observable<TopicSubscription[]> => {
    this.isLoading$.next(true);

    return this.fetchGet<TopicSubscription[]>(
      `${this.API_PATHNAME}/subscribed`
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  };

  /**
   * Retrieves all topics along with subscription status.
   * @returns An Observable containing an array of topics with subscription status.
   */
  public getAllThemesWithSubscription = (): Observable<Topic[]> => {
    this.isLoading$.next(true);

    const themesObs: Observable<Topic[]> = this.getAllThemes();
    const subscribedIdsObs: Observable<TopicSubscription[]> =
      this.getAllSubscribedThemes();

    return combineLatest([themesObs, subscribedIdsObs]).pipe(
      map(([themes, subscribedIds]) => {
        console.log([themes, subscribedIds]);

        return themes.map((theme: Topic) => {
          const isSubscribed = subscribedIds.some((subscription) => {
            return (
              subscription.themeId === theme.id && subscription.isSubscribed
            );
            // Check if the subscription.themeId is present and isSubscribed is true
          });
          return {
            id: theme.id,
            title: theme.title,
            description: theme.description,
            isSubscribed,
          };
        });
      }),
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  };

  /**
   * Subscribes to a topic.
   * @param {number} themeId - The ID of the topic to subscribe to.
   * @returns An Observable containing a message indicating the subscription status.
   */
  public subscribeToTheme = (themeId: number): Observable<Message> => {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Message>(
      `${this.API_PATHNAME}/subscribe/`,
      null, // * Empty body
      params
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  };

  /**
   * Unsubscribes from a topic.
   * @param {number} themeId - The ID of the topic to unsubscribe from.
   * @returns An Observable containing a message indicating the unsubscription status.
   */
  public unsubscribeToTheme = (themeId: number): Observable<Message> => {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Message>(
      `${this.API_PATHNAME}/unsubscribe/`,
      null, // * Empty body
      params
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
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
