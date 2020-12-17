import { Routes } from '@angular/router';
import { CertificatesOfUserComponent } from './certificates-of-user/certificates-of-user.component';
import { CertificatesComponent } from './certificates/certificates.component';
import { LoginComponent } from './login/login.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UsersComponent } from './users/users.component';
import { AdminGuard } from './_guards/admin.guard';
import { UserGuard } from './_guards/user.guard';
import { UserDetailResolver } from './_resolvers/user-detail.resolver';


export const appRoutes: Routes = [
  {
    path: 'users',
    runGuardsAndResolvers: 'always',
    canActivate: [AdminGuard],
    component: UsersComponent,
  },
  {
    path: 'certificates',
    runGuardsAndResolvers: 'always',
    canActivate: [AdminGuard],
    component: CertificatesComponent,
  },
  {
    path: 'user/:id',
    runGuardsAndResolvers: 'always',
    canActivate: [AdminGuard],
    resolve: { user: UserDetailResolver },
    component: UserDetailsComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'my_certs',
    runGuardsAndResolvers: 'always',
    canActivate: [UserGuard],
    component: CertificatesOfUserComponent
  },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];