import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MeComponent } from './features/me/components/me/me.component';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import {HomeComponent} from "./components/home/home.component";
import {PostComponent} from "./features/post/components/post/post.component";

const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    component: HomeComponent
  },
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'posts',
    canActivate: [AuthGuard],
    component: PostComponent
  },
  {
    path: 'topics',
    canActivate: [AuthGuard],
    component: PostComponent
  },
  {
    path: 'me',
    canActivate: [AuthGuard],
    component: MeComponent
  },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
