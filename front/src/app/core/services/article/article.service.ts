import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { Article, ArticleSummary, UserComment } from '@core/types/article.type';
import { Message } from '@core/types/message.type';

/**
 * Service for managing articles, including fetching, creating, and commenting.
 * @Injectable
 * @providedIn: 'root'
 */
@Injectable({
  providedIn: 'root',
})
export class ArticleService extends ApiService {
  /**
   * The base pathname for articles-related API endpoints.
   */
  private readonly API_PATHNAME: string = 'api/articles';

  /**
   * Observable to track the loading state of article-related operations.
   */
  public isLoading$ = new BehaviorSubject<boolean>(false);

  /**
   * Observable to track whether the service is waiting for comments response.
   */
  public isWaitingForCommentsResponse$ = new BehaviorSubject<boolean>(false);

  /**
   * Observable to track if there's an error during comment submission.
   */
  public hasCommentSubmissionError$ = new BehaviorSubject<boolean>(false);

  /**
   * Observable to track error messages.
   */
  public errorMessage$ = new BehaviorSubject<string>('');

  /**
   * Observable to track if there's an error during any operation.
   */
  public hasError$ = new BehaviorSubject<boolean>(false);

  /**
   * Fetches all articles.
   * @returns {Observable<{ articles: Array<ArticleSummary> }>} An Observable with the response data.
   */
  public getAllArticles = (): Observable<{
    articles: Array<ArticleSummary>;
  }> => {
    this.isLoading$.next(true);

    return this.fetchGet<{ articles: Array<ArticleSummary> }>(
      this.API_PATHNAME
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  };

  /**
   * Posts a new article.
   * @param {number} themeId - The ID of the theme to which the article belongs.
   * @param {Pick<Article, 'title' | 'description'>} newArticle - The new article data.
   * @returns {Observable<Message>} An Observable with the response data.
   */
  public postArticle = (
    themeId: number,
    newArticle: Pick<Article, 'title' | 'description'>
  ): Observable<Message> => {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ themeId });

    return this.fetchPost<Pick<Article, 'title' | 'description'>>(
      `${this.API_PATHNAME}/`,
      newArticle,
      params
    ).pipe(tap(this.updateLoadingState), catchError(this.handleErrors));
  };

  /**
   * Fetches an article by its ID.
   * @param {number} articleId - The ID of the article to fetch.
   * @returns {Observable<Article>} An Observable with the response data.
   */
  public getArticleById = (articleId: number): Observable<Article> => {
    this.isLoading$.next(true);

    const params = this.changeObjectParamsToArray({ articleId });

    return this.fetchGet<ArticleSummary>(`${this.API_PATHNAME}/`, params).pipe(
      tap(this.updateLoadingState),
      catchError(this.handleErrors)
    );
  };

  /**
   * Creates a new comment for an article.
   * @param {number} articleId - The ID of the article to which the comment belongs.
   * @param {Omit<UserComment, 'username'>} newComment - The new comment data.
   * @returns {Observable<Message>} An Observable with the response data.
   */
  public createComment = (
    articleId: number,
    newComment: Omit<UserComment, 'username'>
  ): Observable<Message> => {
    this.isWaitingForCommentsResponse$.next(true);

    const params = this.changeObjectParamsToArray({ articleId });

    return this.fetchPost<Pick<Article, 'title' | 'description'>>(
      `${this.API_PATHNAME}/comment/`,
      newComment,
      params
    ).pipe(
      tap(this.updateCommentLoading),
      catchError(this.handleCommentSubmissionErrors)
    );
  };

  /**
   * Updates the loading state for comments.
   * @param {any} value - The value to set.
   * @private
   */
  private updateCommentLoading = (value: any): void => {
    this.isWaitingForCommentsResponse$.next(false);
    this.hasCommentSubmissionError$.next(false);
  };

  /**
   * Updates the loading state.
   * @param {any} value - The value to set.
   * @private
   */
  private updateLoadingState = (value: any): void => {
    this.isLoading$.next(false);
    this.hasError$.next(false);
  };

  /**
   * Handles errors during comment submission.
   * @param {any} err - The error object.
   * @param {any} caught - The caught object.
   * @returns {never} Throws an error.
   * @private
   */
  private handleCommentSubmissionErrors = (err: any, caught: any): never => {
    this.hasCommentSubmissionError$.next(true);

    this.isWaitingForCommentsResponse$.next(false);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  };

  /**
   * Handles general errors related to article.
   * @param {any} err - The error object.
   * @param {any} caught - The caught object.
   * @returns {never} Throws an error.
   * @private
   */
  private handleErrors = (err: any, caught: any): never => {
    this.isLoading$.next(false);
    this.hasError$.next(true);

    this.isWaitingForCommentsResponse$.next(false);

    this.errorMessage$.next(err?.error?.message);

    throw new Error(`An error occurred: ${err?.message}`);
  };
}
