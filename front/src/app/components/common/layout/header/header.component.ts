import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import {
  NavigationEnd,
  Router,
  RouterLink,
  RouterLinkActive,
} from '@angular/router';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { matchesCssMediaQuery } from '@utils/helpers/window.helpers';
import { Subscription, filter } from 'rxjs';

/**
 * Represents the header of the application.
 */
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  /**
   * Reference to the navigation bar element.
   *
   * @type {ElementRef<HTMLElement> | undefined}
   */
  @ViewChild('navigationBarRef') navigationBarRef!: ElementRef<HTMLElement>;

  // * Dependency injections
  /**
   * The Angular Router service.
   */
  private readonly router = inject(Router);

  /**
   * The cookies service to set, get and delete the JWT
   */
  private readonly cookiesService = inject(CookiesService);

  // * Observables
  /**
   * Holds the subscription object for managing router navigation events.
   */
  private routerNavigationEventsSubscription: Subscription = new Subscription();

  /**
   * Represents the authentication status of the user. Initially set to `false`.
   */
  public passedAuthentication: boolean =
    this.router.url !== '/register' &&
    this.router.url !== '/login' &&
    this.cookiesService.getJwt() !== null;

  ngOnInit() {
    // Subscribe to NavigationEnd events to update authentication status on route changes
    this.routerNavigationEventsSubscription = this.router.events
      .pipe(
        filter(
          (event): event is NavigationEnd => event instanceof NavigationEnd
        )
      )
      .subscribe(() => {
        this.updateAuthenticationStatus();
      });
  }

  ngOnDestroy() {
    this.routerNavigationEventsSubscription.unsubscribe();
  }

  /**
   * Updates the authentication status based on the current route.
   */
  private updateAuthenticationStatus = (): void => {
    this.passedAuthentication =
      this.router.url !== '/register' &&
      this.router.url !== '/login' &&
      this.cookiesService.getJwt() !== null;
  };

  /**
   * Toggles the visibility of the mobile burger menu sidebar.
   * @param isActive A boolean indicating whether the sidebar should be active.
   */
  toggleMobileBurgerMenuSidebar = (isActive: boolean): void => {
    const isNotMobile = matchesCssMediaQuery('width > 768px');
    if (isNotMobile || !this.passedAuthentication) {
      return;
    }

    const navigationBar: HTMLElement = this.navigationBarRef.nativeElement;

    navigationBar.classList.toggle('active', isActive);
  };
}
