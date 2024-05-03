import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { UnauthGuard } from './guards/unauth.guard';
import { ThemeComponent } from './pages/theme/component/theme/theme.component';
import { UserComponent } from './pages/user/components/detail/user.component';
import { EditComponent } from './pages/user/components/edit/edit.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: 'home', 
  canActivate: [UnauthGuard], 
  component: HomeComponent 
  },
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'theme',
    component: ThemeComponent
  },
  {
    path: 'detail',
    component: UserComponent
  },
  {
    path: 'edit',
    component: EditComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {

}