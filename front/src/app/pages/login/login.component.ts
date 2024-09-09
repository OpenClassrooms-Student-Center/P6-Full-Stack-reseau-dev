import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '@core/services/auth/auth.service';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserBasicInfo, UserInfo } from '@core/types/user.type';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import {
  bootstrapEyeFill,
  bootstrapEyeSlashFill,
} from '@ng-icons/bootstrap-icons';

import { Store } from '@ngrx/store';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';
import { Subscription } from 'rxjs';

/**
 * Represents the component for user login.
 */
@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    SpinLoaderComponent,
    NgIconComponent,
  ],
  viewProviders: [provideIcons({ bootstrapEyeFill, bootstrapEyeSlashFill })],
})
export class LoginComponent {
  // * Services
  /**
   * Service for managing cookies.
   */
  private readonly cookiesService = inject(CookiesService);

  /**
   * Angular router service for navigation.
   */
  private readonly router = inject(Router);

  /**
   * Authentication service for user login.
   */
  private readonly authService = inject(AuthService);

  /**
   * Form builder service for creating reactive forms.
   */
  private readonly formBuilder = inject(FormBuilder);

  /**
   * Store for managing application state.
   */
  private readonly store = inject(Store);

  /**
   * Holds the timeout ID for managing asynchronous actions.
   */
  public timeoutId!: NodeJS.Timeout;

  // * Signals

  /**
   * Signal indicating whether to show the password.
   */
  public showPassword = signal(false);

  /**
   * Signal indicating whether the login process is loading.
   */
  public isLoading = toSignal<boolean>(this.authService.isLoading$);

  /**
   * Signal indicating whether an error occurred during login.
   */
  public hasError = toSignal<boolean>(this.authService.hasError$);

  /**
   * Signal containing the error message during login.
   */
  public errorMessage = toSignal<string>(this.authService.errorMessage$);

  /**
   * Signal containing user information.
   */
  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  /**
   * Indicates whether a JWT token is present.
   * @type {boolean}
   */
  public hasJwt: boolean = false;

  /**
   * Login form for user authentication.
   */
  public readonly loginForm = this.formBuilder.group({
    identifier: ['', [Validators.required]],
    password: ['', Validators.required],
  });

  ngOnDestroy() {
    clearTimeout(this.timeoutId);

    this.authService.isLoading$.next(false);
    this.authService.hasError$.next(false);
  }

  /**
   * Toggles the visibility of the password input
   */
  togglePasswordVisibility = (): void => {
    this.showPassword.update((oldValue: boolean) => {
      return !oldValue;
    });
  };

  /**
   * Handles the submission of the login form, if login is successful it will store the jwt and redirect to the articles page
   * @param {Event} event - The submit event.
   */
  onSubmit = (event: Event) => {
    event.preventDefault();

    const { identifier, password } = this.loginForm.getRawValue();

    const subscription: Subscription = this.authService
      .login({
        identifier: identifier?.trim() as string,
        password: password?.trim() as string,
      })
      .subscribe((value: UserInfo) => {
        const { token, id, email, username } = value;
        // Setting the cookies
        this.cookiesService.setJwt(token as string);

        this.hasJwt = true;

        // Dispatching an action
        this.store.dispatch(setInfo({ id, email, username }));

        subscription.unsubscribe();

        this.timeoutId = setTimeout(() => {
          this.router.navigate(['/articles']);
        }, 3_000);
      });
  };
}
