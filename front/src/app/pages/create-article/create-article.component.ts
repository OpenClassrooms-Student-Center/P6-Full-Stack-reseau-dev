import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { ArticleService } from '@core/services/article/article.service';
import { ArticleCreationValues } from '@core/types/article.type';
import { Message } from '@core/types/message.type';
import { TopicOptions } from '@core/types/topic.type';
import { WebStorage } from '@lephenix47/webstorage-utility';
import { Subscription } from 'rxjs';

/**
 * Represents the component for creating a new article.
 */
@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [RouterLink, SpinLoaderComponent, ReactiveFormsModule],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss',
})
export class CreateArticleComponent {
  /**
   * Form builder service for creating reactive forms.
   */
  private readonly formBuilder = inject(FormBuilder);

  /**
   * Service for managing article-related operations.
   */
  private readonly articleService = inject(ArticleService);

  /**
   * Signal indicating whether the article creation process is loading.
   */
  public isLoading = toSignal<boolean>(this.articleService.isLoading$);

  /**
   * Signal indicating whether an error occurred during article creation.
   */
  public hasError = toSignal<boolean>(this.articleService.hasError$);

  /**
   * Signal containing the error message during article creation.
   */
  public errorMessage = toSignal<string>(this.articleService.errorMessage$);

  /**
   * Signal indicating whether the article creation was successful.
   */
  public hasSuccess = signal<boolean>(false);

  /**
   * Array of available themes for articles.
   */
  public themesArray: Array<TopicOptions> = [
    { id: 1, theme: 'Python' },
    { id: 2, theme: 'TypeScript' },
    { id: 3, theme: 'Java' },
    { id: 4, theme: 'SEO' },
    { id: 5, theme: 'SASS' },
    { id: 6, theme: 'C++' },
  ];

  /**
   * Default values for the article creation form obtained from local storage.
   */
  public creationFormDefaultValues: ArticleCreationValues | null =
    WebStorage.getKey('article-creation');

  ngOnInit() {
    if (!this.creationFormDefaultValues) {
      this.resetLocalStorage();
    }
  }

  /**
   * Article creation form.
   */
  public readonly createArticleForm = this.formBuilder.group({
    themeId: [
      this.creationFormDefaultValues?.themeId || '1',
      [Validators.required],
    ],
    title: [this.creationFormDefaultValues?.title || '', Validators.required],
    description: [
      this.creationFormDefaultValues?.description || '',
      Validators.required,
    ],
  });

  /**
   * Sets the default value to local storage based on user input.
   * @param {Event} event - The input event.
   */
  public setDefaultValueToLocalStorage = (event: Event): void => {
    const element = event.target as HTMLElement;

    const articleCreationValues =
      WebStorage.getKey<ArticleCreationValues>('article-creation');

    switch (element.tagName.toLowerCase()) {
      case 'select': {
        if ((element as HTMLSelectElement).name === 'topics') {
          articleCreationValues.themeId = (element as HTMLSelectElement).value;
        }
        break;
      }
      case 'input': {
        if ((element as HTMLInputElement).name === 'title') {
          articleCreationValues.title = (element as HTMLInputElement).value;
        }
        break;
      }
      case 'textarea': {
        if ((element as HTMLTextAreaElement).name === 'description') {
          articleCreationValues.description = (
            element as HTMLTextAreaElement
          ).value;
        }
        break;
      }

      default:
        break;
    }

    WebStorage.setKey('article-creation', articleCreationValues);
  };

  /**
   * Handles the submission of the article creation form.
   * @param {Event} event - The form submission event.
   */
  public onSubmit = (event: Event): void => {
    event.preventDefault();

    const { themeId, title, description } =
      this.createArticleForm.getRawValue();

    const normalizedThemeId: number = Number(themeId as string);

    const normalizedBody = {
      title: title?.trim() as string,
      description: description?.trim() as string,
    };

    const subscription: Subscription = this.articleService
      .postArticle(normalizedThemeId, normalizedBody)
      .subscribe((value: Message) => {
        this.hasSuccess.update(() => {
          return true;
        });

        this.resetForm();
        this.resetLocalStorage();

        subscription.unsubscribe();
      });
  };

  /**
   * Resets the article creation form inputs.
   */
  private resetForm() {
    this.createArticleForm.setValue({
      title: '',
      themeId: '1',
      description: '',
    });
  }

  /**
   * Resets the local storage values related to article creation.
   */
  private resetLocalStorage() {
    WebStorage.setKey('article-creation', {
      themeId: '1',
      title: '',
      description: '',
    });
  }
}
