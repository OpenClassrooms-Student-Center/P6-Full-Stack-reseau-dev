import { Routes } from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {LoginComponent} from "./pages/authentication/login/login.component";
import {UnauthenticatedComponent} from "./pages/authentication/unauthenticated/unauthenticated.component";
import {RegisterComponent} from "./pages/authentication/register/register.component";
import {ArticleComponent} from "./pages/article/article/article.component";
import {CreateArticleComponent} from "./pages/article/create-article/create-article.component";
import {ThemesComponent} from "./pages/themes/themes.component";
import {ProfileComponent} from "./pages/profile/profile.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {NoAuthGuard} from "./core/guards/unauth.guard";

export const routes: Routes = [
  {
    path: 'themes', component: ThemesComponent,
  },
  {
    path: 'profile', component: ProfileComponent,
  },
  {
    path: 'article/create', component: CreateArticleComponent,
  },
  {
    path: 'article/:id', component: ArticleComponent,
  },
  {
    path: 'article/update/:id', component: CreateArticleComponent,
  },
  {
    path: 'welcome', component: UnauthenticatedComponent,
  },
  {
    path: 'login', component: LoginComponent,
  },
  {
    path: 'register', component: RegisterComponent,
  },
  {
    path: 'home', component: HomeComponent,
  },
  {
    path: '', redirectTo: '/home', pathMatch: 'full'
  },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];
