import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import {  RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from './app-routing.module';
import {  HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';
import { HeaderComponent } from './shared/components/header/header.component';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { SharedModule } from './shared/shared.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [AppComponent,HeaderComponent],
  imports: [ BrowserModule,ReactiveFormsModule,RouterModule,AppRoutingModule, SharedModule,NoopAnimationsModule],
  bootstrap: [AppComponent],
  providers:[ provideHttpClient(),{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }]
})
export class AppModule {}


