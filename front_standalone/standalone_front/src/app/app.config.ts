import {ApplicationConfig, isDevMode} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideAnimationsAsync} from "@angular/platform-browser/animations/async";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {TranslocoHttpLoader} from "./transloco-loader";
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptors, withInterceptorsFromDi} from "@angular/common/http";
import {provideTransloco} from "@jsverse/transloco";
import {provideToastr} from "ngx-toastr";
import {provideAnimations} from "@angular/platform-browser/animations";
import {JwtInterceptor} from "./core/interceptors/jwt.interceptor";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    provideAnimationsAsync(),
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    provideHttpClient(withInterceptorsFromDi()),
    {
      provide:HTTP_INTERCEPTORS,
      useClass:JwtInterceptor,
      multi:true
    },
    provideTransloco({
    config: {
      availableLangs: ['fr', 'en', 'de'],
      defaultLang: 'fr',
      // Remove this option if your application doesn't support changing language in runtime.
      reRenderOnLangChange: true,
      prodMode: !isDevMode(),
    },
    loader: TranslocoHttpLoader
  }),
    provideAnimations(), // required animations providers
    provideToastr({
      timeOut: 2000,
      positionClass: "toast-bottom-right",
    }), // Toastr providers
  ]
};
