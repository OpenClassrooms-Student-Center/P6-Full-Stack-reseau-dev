import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { CookieType } from '@lephenix47/cookies-utility';

/**
 * Guard function to protect routes that should not be accessible to authenticated users.
 * Redirects authenticated users to the articles page.
 * @param route The activated route snapshot.
 * @param state The router state snapshot.
 * @returns A boolean indicating whether the route can be activated.
 */
export const unauthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean => {
  const router = inject(Router);

  const cookiesService = inject(CookiesService);

  const jwtCookie: CookieType | null = cookiesService.getJwt();

  if (jwtCookie) {
    router.navigate(['/articles']);
    return false;
  } else {
    return true;
  }
};
