import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ArticlePageComponent } from './pages/article-page/article-page.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthService } from './services/auth.service';
import { AuthInterceptor } from './interceptor/auth-interceptor';
import { NavbarComponent } from './navbar/navbar.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import { ThemesPageComponent } from './pages/themes-page/themes-page.component';

@NgModule({
  declarations: [AppComponent, 
                HomeComponent, 
                LoginComponent, 
                RegisterComponent, 
                ArticlePageComponent, 
                NavbarComponent, 
                CreateArticleComponent,
                ThemesPageComponent,
              ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,


  ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],  bootstrap: [AppComponent],
})
export class AppModule {}
