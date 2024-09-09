import { HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { CookiesService } from '@core/services/cookies/cookies.service';
import { CookieType } from '@lephenix47/cookies-utility';

/**
 * Interceptor function to attach JWT token to outgoing HTTP requests if present in the cookies
 * @param req The HTTP request object.
 * @param next The HTTP handler for the request.
 * @returns An observable of the HTTP event.
 */
export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const cookiesService = inject(CookiesService);

  const jwtCookie: CookieType | null = cookiesService.getJwt();

  const jwt: Partial<string> = jwtCookie?.value;
  if (!jwt) {
    return next(req);
  }

  const requestWithToken: HttpRequest<unknown> = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${jwt}`),
  });

  return next(requestWithToken);
};
