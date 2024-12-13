import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';

// Composants de l'application
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticlePageComponent } from './pages/article-page/article-page.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import { ThemesPageComponent } from './pages/themes-page/themes-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { ArticleIdPageComponent } from './pages/article-id-page/article-id-page.component';

// Services et interceptors
import { AuthService } from './services/auth.service';
import { AuthInterceptor } from './interceptor/auth-interceptor';

@NgModule({
  declarations: [
    // Déclaration des composants utilisés dans l'application
    AppComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ArticlePageComponent,
    CreateArticleComponent,
    ThemesPageComponent,
    ProfilePageComponent,
    ArticleIdPageComponent,
  ],
  imports: [
    // Importation des modules nécessaires pour le fonctionnement de l'application
    BrowserModule,
    BrowserAnimationsModule,  // Module pour les animations Angular
    HttpClientModule,         // Module pour effectuer des requêtes HTTP
    FormsModule,              // Module pour les formulaires simples
    ReactiveFormsModule,       // Module pour les formulaires réactifs
    AppRoutingModule,          // Module de routage pour la navigation entre pages
    MatButtonModule,           // Module Material pour les boutons
    MatSidenavModule,          // Module Material pour les barres latérales
    MatToolbarModule,          // Module Material pour les barres d'outils
    MatIconModule,             // Module Material pour les icônes
    MatListModule,             // Module Material pour les listes
    MatFormFieldModule,        // Module Material pour les champs de formulaire
    MatSelectModule,           // Module Material pour les menus déroulants
    MatSnackBarModule,         // Module Material pour les notifications
  ],
  providers: [
    // Définition des services et des interceptors
    AuthService,               // Service d'authentification
    {
      provide: HTTP_INTERCEPTORS, 
      useClass: AuthInterceptor, // Intercepteur pour ajouter le token d'authentification aux requêtes HTTP
      multi: true,               // Permet d'utiliser plusieurs interceptors
    },
  ],
  bootstrap: [AppComponent],   // Composant racine à démarrer lors du bootstrap de l'application
})
export class AppModule {}
