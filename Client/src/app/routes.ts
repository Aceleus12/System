import { Routes } from '@angular/router';
import { CertificatesOfUserComponent } from './certificates-of-user/certificates-of-user.component';
import { CertificatesComponent } from './certificates/certificates.component';
import { LoginComponent } from './login/login.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UsersComponent } from './users/users.component';
import { UserDetailResolver } from './_resolvers/user-detail.resolver';


export const appRoutes: Routes = [
  {
    path: 'users',
    component: UsersComponent,
  },
  {
    path: 'certificates',
    component: CertificatesComponent,
  },
  {
    path: 'user/:id',
    resolve: { user: UserDetailResolver },
    component: UserDetailsComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'my_certs',
    component: CertificatesOfUserComponent
  },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];