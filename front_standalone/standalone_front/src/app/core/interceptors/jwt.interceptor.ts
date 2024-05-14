import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {AuthService} from "../services/auth.service";
import {JWTService} from "../services/jwt.service";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService,
              private jwtService: JWTService,
              ) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    if (this.jwtService.hasValidToken()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.jwtService.getToken()}`,
        },
      });
    }
    return next.handle(request);
  }
}
