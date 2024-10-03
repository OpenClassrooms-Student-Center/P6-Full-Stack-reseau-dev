import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticlePageComponent } from './pages/article-page/article-page.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component'; 
import { ThemesPageComponent } from './pages/themes-page/themes-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';


// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfilePageComponent},
  { path: 'article', component: ArticlePageComponent },
  { path: 'article/add', component: CreateArticleComponent },
  { path: 'themes', component: ThemesPageComponent},
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
