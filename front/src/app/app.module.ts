import {LOCALE_ID, NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {registerLocaleData} from "@angular/common";
import localeFr from '@angular/common/locales/fr';
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {NavbarComponent} from "./components/navbar/navbar.component";
registerLocaleData(localeFr, 'fr');
@NgModule({
  declarations: [AppComponent, HomeComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    NavbarComponent,
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr' },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
