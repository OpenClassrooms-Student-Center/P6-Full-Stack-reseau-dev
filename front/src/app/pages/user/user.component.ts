import { Component, inject, signal } from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserBasicInfo } from '@core/types/user.type';
import { Store } from '@ngrx/store';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserService } from '@core/services/user/user.service';
import { TopicService } from '@core/services/topic/topic.service';
import { Message } from '@core/types/message.type';
import { Subscription } from 'rxjs';
import { WebStorage } from '@lephenix47/webstorage-utility';

/**
 * Component representing the user profile and settings page
 */
@Component({
  selector: 'app-user',
  standalone: true,
  imports: [TopicsContainerComponent, ReactiveFormsModule, SpinLoaderComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss',
})
export class UserComponent {
  // * Services
  /**
   * Angular router service.
   */
  private readonly router = inject(Router);

  /**
   * NgRx store service for managing application state.
   */
  private readonly store = inject(Store);

  /**
   * Cookies service for managing browser cookies.
   */
  private readonly cookiesService = inject(CookiesService);

  /**
   * User service for managing user data and authentication.
   */
  private readonly userService = inject(UserService);

  /**
   * Topic service for managing topics and subscriptions.
   */
  private readonly topicService = inject(TopicService);

  /**
   * Form builder service for creating Angular reactive forms.
   */
  private readonly formBuilder = inject(FormBuilder);

  // * Signals
  /**
   * Signal representing user basic information fetched from the store.
   */
  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  /**
   * Signal indicating whether topics are currently loading.
   */
  public topicsAreLoading = toSignal<boolean>(this.topicService.isLoading$);

  /**
   * Signal indicating whether there was an error loading topics.
   */
  public topicsHaveAnError = toSignal<boolean>(this.topicService.hasError$);

  /**
   * Signal containing the error message related to topics.
   */
  public topicsErrorMessage = toSignal<string>(this.topicService.errorMessage$);

  /**
   * Signal indicating whether the user update operation is currently loading.
   */
  public userUpdateIsLoading = toSignal<boolean>(this.userService.isLoading$);

  /**
   * Signal indicating whether there was an error updating user information.
   */
  public userHasAnError = toSignal<boolean>(this.userService.hasError$);

  /**
   * Signal containing the error message related to user operations.
   */
  public userErrorMessage = toSignal<string>(this.userService.errorMessage$);

  /**
   * String containing the success message after user operations.
   */
  public userSuccessMessage: string = '';

  /**
   * Signal containing the array of subscribed topics.
   */
  public topicsArray = signal<Topic[]>([]);

  /**
   * Form group for user credentials, including username and email.
   */
  public readonly userCredentialsForm = this.formBuilder.group({
    username: [this.userInfo()?.username, Validators.required],
    email: [this.userInfo()?.email, [Validators.required]],
  });

  ngOnInit() {
    this.initializeTopicsArray();
  }

  /**
   * Fetches subscribed topics from the topic service.
   * Subscribed topics are then stored in the topics array.
   */
  private initializeTopicsArray = (): void => {
    const subscription: Subscription = this.topicService
      .getAllThemesWithSubscription()
      .subscribe((res: Topic[]) => {
        const subscribedTopics: Topic[] = res.filter(
          (t: Topic) => t.isSubscribed
        );

        this.topicsArray.update(() => {
          return subscribedTopics;
        });

        subscription.unsubscribe();
      });
  };

  /**
   * Handles the submission of the user credentials form.
   * Updates user information based on the form input.
   * @param {Event} event - The form submission event.
   */
  onSubmit = (event: Event): void => {
    event.preventDefault();

    this.userSuccessMessage = '';

    const { username, email } = this.userCredentialsForm.getRawValue();

    const subscription: Subscription = this.userService
      .updateUser({
        username: username?.trim() as string,
        email: email?.trim() as string,
      })
      .subscribe((result: Message) => {
        this.userSuccessMessage = result.message;

        subscription.unsubscribe();
      });
  };

  /**
   * Logs out the user by deleting the JWT token and redirecting to the home page.
   *
   * Also resets the local storage article-creation object for the article page
   */
  logout = (): void => {
    this.cookiesService.deleteJwt();

    WebStorage.setKey('article-creation', {
      themeId: '1',
      title: '',
      description: '',
    });

    this.router.navigate(['/']);
  };

  /**
   * Toggles the user's subscription to a topic.
   * @param {number} id - The ID of the topic to subscribe or unsubscribe.
   */
  updateUserThemeSubscription = (id: number) => {
    this.updateTopicsArray(id);

    const subscription: Subscription = this.topicService
      .unsubscribeToTheme(id)
      .subscribe(() => {
        this.updateTopicsArray(id);

        subscription.unsubscribe();
      });
  };

  /**
   * Updates the topics array by removing the specified topic.
   * @param {number} id - The ID of the topic to remove from the array.
   */
  private updateTopicsArray = (id: number): void => {
    this.topicsArray.update((topics) => topics.filter((t) => t.id !== id));
  };
}
