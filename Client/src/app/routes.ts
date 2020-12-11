import { Routes } from '@angular/router';
import { CertificatesComponent } from './certificates/certificates.component';
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
  { path: '**', redirectTo: '', pathMatch: 'full' },
];