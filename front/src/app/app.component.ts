import { Component, inject } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/common/layout/header/header.component';
import { Subscription, filter, take } from 'rxjs';
import { Store } from '@ngrx/store';
import { toSignal } from '@angular/core/rxjs-interop';
import { UserBasicInfo, UserEntity } from '@core/types/user.type';
import { UserService } from '@core/services/user/user.service';
import { setInfo } from '@mdd-global-state-ngrx/actions/user-info.actions';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { CookieType } from '@lephenix47/cookies-utility';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [RouterOutlet, HeaderComponent],
})
export class AppComponent {
  title = 'mdd-app';

  private cookiesService = inject(CookiesService);

  private store = inject(Store);

  public userService = inject(UserService);

  public userInfo = toSignal<UserBasicInfo>(this.store.select('userInfo'));

  // * Instance properties
  public showHeader = true;

  // * Dependency injections
  public router = inject(Router);

  // * Observables
  private navigationEndEvents$ = this.router.events.pipe(
    filter((event): event is NavigationEnd => {
      return event instanceof NavigationEnd;
    })
  );

  ngOnInit() {
    const jwt: CookieType | null = this.cookiesService.getJwt();
    if (jwt) {
      const subscription: Subscription = this.userService
        .getUser()
        .pipe(take(1))
        .subscribe((userInfo: UserEntity) => {
          const { id, email, username } = userInfo;

          this.store.dispatch(setInfo({ id, email, username }));

          subscription.unsubscribe();
        });
    }

    this.navigationEndEvents$.subscribe(() => {
      this.showHeader = this.router.url !== '/home';
    });
  }
}
