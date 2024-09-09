import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Article, UserComment } from '@core/types/article.type';
import { CommentsComponent } from '@components/common/article/comments/comments.component';
import { toSignal } from '@angular/core/rxjs-interop';
import { ArticleService } from '@core/services/article/article.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { Store } from '@ngrx/store';
import { UserBasicInfo } from '@core/types/user.type';
import { Message } from '@core/types/message.type';
import { Title } from '@angular/platform-browser';
import { Subscription } from 'rxjs';

/**
 * Represents the component for displaying an article.
 */
@Component({
  selector: 'app-article',
  standalone: true,
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss',
  imports: [
    RouterLink,
    CommentsComponent,
    SpinLoaderComponent,
    ReactiveFormsModule,
  ],
})
export class ArticleComponent {
  /**
   * Service for managing the title meta tag.
   */
  private readonly titleMetaTagService = inject(Title);

  /**
   * NgRx Store for managing application state.
   */
  private readonly store = inject(Store);

  /**
   * Service for managing articles.
   */
  private readonly articleService = inject(ArticleService);

  /**
   * Form builder service for creating reactive forms.
   */
  private readonly formBuilder = inject(FormBuilder);

  /**
   * The activated route service.
   */
  private readonly activatedRoute = inject(ActivatedRoute);

  /**
   * Signal representing user information.
   */
  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  /**
   * Signal indicating whether the article is loading.
   */
  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  /**
   * Signal indicating whether the component is waiting for comments response.
   */
  public isWaitingForCommentsResponse = toSignal<boolean>(
    this.articleService.isWaitingForCommentsResponse$
  );

  /**
   * Signal indicating whether an error has occurred while fetching the article.
   */
  public hasError = toSignal<boolean>(this.articleService.hasError$);

  /**
   * Signal indicating whether there was an error during comment submission.
   */
  public hasCommentSubmissionError = toSignal<boolean>(
    this.articleService.hasCommentSubmissionError$
  );

  /**
   * Signal containing the error message when the article fetch fails.
   */
  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  /**
   * Signal indicating whether the article fetch operation was successful.
   */
  public hasSuccess = signal<boolean>(false);

  /**
   * The ID of the article taken from the URI
   */
  public id = Number(this.activatedRoute.snapshot.params['id']);

  /**
   * Signal containing the comments of the article.
   */
  public articleComments = signal<UserComment[]>([]);

  /**
   * Signal containing the current article information
   */
  public currentArticle = signal<Omit<Article, 'comments'>>({
    id: this.id,
    authorName: '',
    title: '',
    description: '',
    theme: '',
    creationDate: '',
  });

  /**
   * The form builder for creating comments.
   */
  public readonly commentCreationForm = this.formBuilder.group({
    comment: ['', [Validators.required]],
  });

  ngOnInit() {
    this.setArticle();
  }

  /**
   * Handles the submission of a comment.
   * @param {Event} event - The submit event.
   */
  public onCommentSubmission = (event: Event): void => {
    event.preventDefault();

    const { comment } = this.commentCreationForm.getRawValue();

    const subscription: Subscription = this.articleService
      .createComment(this.id, { comment: comment?.trim() as string })
      .subscribe((message: Message) => {
        const { username } = this.userInfo() as UserBasicInfo;

        const newComment = { username, comment } as UserComment;

        this.articleComments.update((previousComments) => {
          return [...previousComments, newComment];
        });

        this.resetTextAreaValue();

        subscription.unsubscribe();
      });
  };

  /**
   * Submits a comment when the user presses the `Ctrl` + `Enter` key combination.
   *
   * @param {KeyboardEvent} event - The keyboard event triggered by the user.
   * @returns {void}
   */
  submitCommentWithShortcut = (event: KeyboardEvent): void => {
    const userWantsToSendCommentWithShortcut: boolean =
      event.ctrlKey && event.key === 'Enter';

    if (userWantsToSendCommentWithShortcut) {
      this.onCommentSubmission(event);
    }
  };

  /**
   * Sets the article and updates the title meta tag.
   */
  private setArticle = (): void => {
    this.titleMetaTagService.setTitle(
      `Récupération de l'article d'ID: ${this.id}`
    );

    const subscription: Subscription = this.articleService
      .getArticleById(this.id)
      .subscribe((article: Article) => {
        article.creationDate = new Date(
          article.creationDate
        ).toLocaleDateString();

        this.articleComments.update(() => {
          return article.comments;
        });

        this.currentArticle.update(() => {
          return article;
        });

        this.titleMetaTagService.setTitle(article.title);

        subscription.unsubscribe();
      });
  };

  /**
   * Resets the value of the comment text area.
   */
  private resetTextAreaValue = (): void => {
    this.commentCreationForm.setValue({
      comment: '',
    });
  };
}
