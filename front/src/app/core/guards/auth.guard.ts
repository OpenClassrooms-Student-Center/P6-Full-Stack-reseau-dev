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
 * Guard function to protect routes that require authentication.
 * Redirects the user to the login page if not authenticated.
 * @param route The activated route snapshot.
 * @param state The router state snapshot.
 * @returns A boolean indicating whether the route can be activated.
 */
export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean => {
  const router = inject(Router);

  const cookiesService = inject(CookiesService);

  const jwtCookie: CookieType | null = cookiesService.getJwt();

  if (jwtCookie) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
