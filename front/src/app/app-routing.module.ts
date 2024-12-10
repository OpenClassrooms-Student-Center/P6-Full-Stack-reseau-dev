import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ArticlesComponent} from "./pages/articles/articles.component";
import {AuthGuard} from "./guards/auth.guard";
import {ProfileComponent} from "./pages/profile/profile.component";
import {SingleArticleComponent} from "./pages/articles/single-article/single-article.component";
import {CreateArticleComponent} from "./pages/articles/create-article/create-article.component";
import {ThemesComponent} from "./pages/themes/themes.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'articles', canActivate: [AuthGuard],  component: ArticlesComponent },
  { path: 'themes', canActivate: [AuthGuard],  component: ThemesComponent },
  { path: 'single-article/:id', canActivate: [AuthGuard],  component: SingleArticleComponent },
  { path: 'create-article', canActivate: [AuthGuard],  component: CreateArticleComponent },
  { path: 'profile', canActivate: [AuthGuard],  component: ProfileComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
