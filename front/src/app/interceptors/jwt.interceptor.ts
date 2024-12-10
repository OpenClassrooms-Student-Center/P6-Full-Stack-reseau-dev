import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {TokenModel} from "../models/TokenModel";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  private readonly whitelistUrls = [
    '/login',
    '/register'
  ];
  /**
   * Intercepts HTTP requests and adds an Authorization header with a Bearer token if the request is not whitelisted.
   * If the request URL matches a whitelisted URL (e.g., '/login' or '/register'), the request proceeds without the token.
   * If a token is found in local storage, it is added to the request headers.
   *
   * @param request The outgoing HTTP request.
   * @param next The next handler in the HTTP pipeline.
   * @return The handled HTTP request, potentially with an Authorization header if applicable.
   */
  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    const isWhitelisted = this.whitelistUrls.some(url => request.url.includes(url));

    if (isWhitelisted) {
      return next.handle(request);
    }

    let tokenJson: string | null = localStorage.getItem('tokenModel');
    let tokenModel: TokenModel = {} as TokenModel;

    if (tokenJson) {
      tokenModel = JSON.parse(tokenJson);
    }

    const token = tokenModel.token;

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(request);
  }
}
