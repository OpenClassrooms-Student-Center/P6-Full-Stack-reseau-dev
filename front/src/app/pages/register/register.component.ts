import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { SpinLoaderComponent } from '@components/shared/spin-loader/spin-loader.component';
import { AuthService } from '@core/services/auth/auth.service';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { UserBasicInfo, UserInfo } from '@core/types/user.type';

import { NgIconComponent, provideIcons } from '@ng-icons/core';

import {
  bootstrapEyeFill,
  bootstrapEyeSlashFill,
} from '@ng-icons/bootstrap-icons';

import { Store } from '@ngrx/store';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';
import { Subscription } from 'rxjs';

/**
 * Represents the component for the registration page.
 */
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    SpinLoaderComponent,
    NgIconComponent,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
  viewProviders: [provideIcons({ bootstrapEyeFill, bootstrapEyeSlashFill })],
})
export class RegisterComponent {
  // * Services
  /**
   * The CookiesService for managing cookies.
   */
  private readonly cookiesService = inject(CookiesService);

  /**
   * The Angular Router service.
   */
  private readonly router = inject(Router);

  /**
   * The AuthService for handling user authentication.
   */
  private readonly authService = inject(AuthService);

  /**
   * The FormBuilder for building and managing forms.
   */
  private readonly formBuilder = inject(FormBuilder);

  /**
   * The NgRx Store for managing application state.
   */
  private readonly store = inject(Store);

  /**
   * Holds the timeout ID for managing asynchronous actions.
   */
  public timeoutId!: NodeJS.Timeout;

  // * Signals
  /**
   * A signal indicating whether to show the password during registration.
   */
  public showPassword = signal(false);

  /**
   * A signal indicating whether the registration process is in progress.
   */
  public isLoading = toSignal<boolean>(this.authService.isLoading$);

  /**
   * A signal indicating whether an error occurred during registration.
   */
  public hasError = toSignal<boolean>(this.authService.hasError$);

  /**
   * A signal containing the error message during registration.
   */
  public errorMessage = toSignal<string>(this.authService.errorMessage$);

  /**
   * A signal containing the basic information of the registered user.
   */
  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  /**
   * Indicates whether a JWT token is present.
   */
  public hasJwt: boolean = false;

  /**
   * The registration form to collect user details.
   */
  public readonly registerForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
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
  togglePasswordVisibility() {
    this.showPassword.update((oldValue: boolean) => {
      return !oldValue;
    });
  }

  /**
   * Handles the form submission for user registration.
   * @param event The form submission event.
   */
  onSubmit = (event: Event) => {
    event.preventDefault();

    const { username, email, password } = this.registerForm.getRawValue();

    const subscription: Subscription = this.authService
      .register({
        username: username?.trim() as string,
        email: email?.trim() as string,
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
