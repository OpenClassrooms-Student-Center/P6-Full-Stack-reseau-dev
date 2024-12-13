import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticlePageComponent } from './pages/article-page/article-page.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import { ThemesPageComponent } from './pages/themes-page/themes-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { ArticleIdPageComponent } from './pages/article-id-page/article-id-page.component';

// Définition des routes de l'application

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfilePageComponent },
  { path: 'article', component: ArticlePageComponent },
  { path: 'article/add', component: CreateArticleComponent },
  { path: 'article/:id', component: ArticleIdPageComponent },
  { path: 'themes', component: ThemesPageComponent },
  { path: '**', redirectTo: '' },
];

// Déclaration du module de routage

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
