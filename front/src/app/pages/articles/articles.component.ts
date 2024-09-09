import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ArticleSummary } from '@core/types/article.type';
import { ArticlesSummaryComponent } from '@components/common/articles/articles-summary/articles-summary.component';
import { Subscription } from 'rxjs';
import { Store } from '@ngrx/store';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserBasicInfo } from '@core/types/user.type';
import { ArticleService } from '@core/services/article/article.service';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';

/**
 * Represents the component for displaying a list of articles.
 */
@Component({
  selector: 'app-articles',
  standalone: true,
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
  imports: [RouterLink, ArticlesSummaryComponent, SpinLoaderComponent],
})
export class ArticlesComponent {
  /**
   * NgRx store for managing application state.
   */
  private readonly store = inject(Store);

  /**
   * Service for managing article-related operations.
   */
  private readonly articleService = inject(ArticleService);

  /**
   * Signal containing user information.
   */
  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  /**
   * Signal indicating the sort order of articles.
   */
  public isAscending = signal<boolean>(false);

  /**
   * Signal indicating whether articles are loading.
   */
  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  /**
   * Signal indicating whether an error occurred while getting the list of articles.
   */
  public hasError = toSignal<boolean>(this.articleService.hasError$);

  /**
   * Signal containing the error message if an error occurred while getting the list of articles.
   */
  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  /**
   * Signal containing an array of articles.
   */
  public arrOfArticles = signal<{ articles: Array<ArticleSummary> }>({
    articles: [],
  });

  /**
   * Toggles the order of articles based on publication date.
   */
  changeArticlesOrder = (): void => {
    this.isAscending.update((previousValue) => {
      return !previousValue;
    });

    const sortedArray = this.arrOfArticles().articles.sort(
      (a: ArticleSummary, b: ArticleSummary) => {
        const difference =
          new Date(a.publicationDate).getTime() -
          new Date(b.publicationDate).getTime();

        return this.isAscending() ? -1 * difference : difference;
      }
    );

    this.arrOfArticles.update(() => {
      return {
        articles: sortedArray,
      };
    });
  };

  ngOnInit() {
    const subscription: Subscription = this.articleService
      .getAllArticles()
      .subscribe((articlesArray) => {
        this.arrOfArticles.update(() => {
          return articlesArray;
        });

        subscription.unsubscribe();
      });
  }
}
