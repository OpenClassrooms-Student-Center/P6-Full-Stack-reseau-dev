import {ApplicationConfig, isDevMode} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideAnimationsAsync} from "@angular/platform-browser/animations/async";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {TranslocoHttpLoader} from "./transloco-loader";
import {provideHttpClient} from "@angular/common/http";
import {provideTransloco} from "@jsverse/transloco";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideAnimationsAsync(), {provide: LocationStrategy, useClass: HashLocationStrategy}, provideHttpClient(), provideTransloco({
    config: {
      availableLangs: ['fr', 'en', 'de'],
      defaultLang: 'fr',
      // Remove this option if your application doesn't support changing language in runtime.
      reRenderOnLangChange: true,
      prodMode: !isDevMode(),
    },
    loader: TranslocoHttpLoader
  })]
};
