import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MeComponent } from './features/me/components/me/me.component';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import {HomeComponent} from "./components/home/home.component";
import {PostComponent} from "./features/post/components/post/post.component";
import {TopicComponent} from "./features/topic/components/topic/topic.component";
import {PostsComponent} from "./features/post/components/posts/posts.component";
import {NewPostComponent} from "./features/post/components/new-post/new-post.component";
import {NotFoundComponent} from "./shared/components/not-found/not-found.component";

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
    path: 'post/new',
    canActivate: [AuthGuard],
    component: NewPostComponent
  },
  {
    path: 'post/:id',
    canActivate: [AuthGuard],
    component: PostComponent
  },
  {
    path: 'posts',
    canActivate: [AuthGuard],
    component: PostsComponent
  },
  {
    path: 'topics',
    canActivate: [AuthGuard],
    component: TopicComponent
  },
  {
    path: 'me',
    canActivate: [AuthGuard],
    component: MeComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
