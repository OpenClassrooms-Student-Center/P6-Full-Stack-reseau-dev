import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ArticleModel} from "../models/ArticleModel";
import {environment} from "../../environments/environment";
import {ThemeModelDto} from "../models/ThemeModelDto";
import {CommentDto} from "../models/CommentDto";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  prefix: string = "/article/";
  private http: HttpClient = inject(HttpClient);
  /**
   * Retrieves all articles with an option to sort in reverse order.
   *
   * @param reverse Whether to sort the articles in reverse chronological order.
   * @return An Observable containing the list of articles.
   */
  getAllArticles(reverse:boolean):Observable<any>{
    return this.http.get<ArticleModel[]>(environment.developpement + this.prefix + `all?reverse=${reverse}`)
  }
  /**
   * Creates a new article using the provided article model.
   *
   * @param article The model containing the article data.
   * @return An Observable containing the server's response after creating the article.
   */
  createArticle(article:ArticleModel):Observable<any>{
    return this.http.post<ArticleModel>(environment.developpement + this.prefix + 'create',article);
  }
  /**
   * Retrieves a single article by its ID.
   *
   * @param id The ID of the article to retrieve.
   * @return An Observable containing the article data.
   */
  getSingleArticle(id:number):Observable<any>{
    return this.http.get<ArticleModel>(environment.developpement + this.prefix + 'single/' + id);
  }
  /**
   * Retrieves all available themes.
   *
   * @return An Observable containing the list of themes.
   */
  getThemes(): Observable<any>{
    return this.http.get<ThemeModelDto[]>(environment.developpement + this.prefix + 'theme/all')
  }
  /**
   * Subscribes the user to a specific theme.
   *
   * @param themeId The ID of the theme to subscribe to.
   * @return An Observable containing the server's response.
   */
  subscribeToTheme(themeId: number): Observable<any> {
    return this.http.post(`${environment.developpement + this.prefix }subscribe/${themeId}`, {});
  }
  /**
   * Unsubscribes the user from a specific theme.
   *
   * @param themeId The ID of the theme to unsubscribe from.
   * @return An Observable containing the server's response.
   */
  unsubscribeFromTheme(themeId: number): Observable<any> {
    return this.http.post(`${environment.developpement + this.prefix }unsubscribe/${themeId}`, {});
  }
  /**
   * Adds a comment to a specific article.
   *
   * @param articleId The ID of the article to comment on.
   * @param comment The DTO containing the comment data.
   * @return An Observable containing the server's response after adding the comment.
   */
  addComment(articleId: number, comment: CommentDto): Observable<any> {
    return this.http.post(`${environment.developpement + this.prefix + articleId + "/"}comment`, comment);
  }
}
