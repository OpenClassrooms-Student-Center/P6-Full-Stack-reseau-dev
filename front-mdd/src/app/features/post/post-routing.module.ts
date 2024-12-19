import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { PostCreateComponent } from './components/post-create/post-create.component';



const routes: Routes = [
  { title: 'post - list', path: 'list', component: PostListComponent },
  { title:'post - detail', path:'detail/:id', component: PostDetailComponent},
  { title:'post - create', path:'create', component: PostCreateComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostRoutingModule { }
