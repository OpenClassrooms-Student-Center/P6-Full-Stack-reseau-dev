import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './shared/components/home/home.component';

import { NgModule } from '@angular/core';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' }, // Route par défaut
    { path: 'home',     canActivate: [UnauthGuard],
        component: HomeComponent },
    { path: 'auth',    
        loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule) },
    { path: 'subscription',     canActivate: [AuthGuard],
        loadChildren: () => import('./features/topic/topic.module').then(m => m.TopicModule) },
        
        { 
            path:'post', canActivate:[AuthGuard],
            loadChildren:() => import('./features/post/post.module').then(m =>m.PostModule)
        }



];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
  })
  export class AppRoutingModule {}