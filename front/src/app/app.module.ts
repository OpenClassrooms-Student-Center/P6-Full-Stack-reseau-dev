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
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { ArticleIdPageComponent } from './pages/article-id-page/article-id-page.component';


@NgModule({
  declarations: [AppComponent, 
                HomeComponent, 
                LoginComponent, 
                RegisterComponent, 
                ArticlePageComponent, 
                NavbarComponent, 
                CreateArticleComponent,
                ThemesPageComponent,
                ProfilePageComponent,
                ArticleIdPageComponent
              ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule
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
