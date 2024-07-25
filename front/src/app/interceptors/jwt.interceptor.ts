import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {AuthService} from "../features/auth/services/auth.service";
import {SessionInformation} from "../features/auth/interfaces/sessionInformation.interface";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  public intercept(request: HttpRequest<SessionInformation>, next: HttpHandler) {
    if (this.authService.isLogged()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.authService.getToken()}`,
        },
      });
    }
    return next.handle(request);
  }
}
