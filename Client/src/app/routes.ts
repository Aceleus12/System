import { Routes } from '@angular/router';
import { CertificatesOfUserComponent } from './certificates-of-user/certificates-of-user.component';
import { CertificatesComponent } from './certificates/certificates.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { FailureComponent } from './failure/failure.component';
import { LoginComponent } from './login/login.component';
import { MoneyComponent } from './money/money.component';
import { SuccessComponent } from './success/success.component';
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
    path: 'failure',
    runGuardsAndResolvers: 'always',
    canActivate: [UserGuard],
    component: FailureComponent
  },
  {
    path: 'success',
    runGuardsAndResolvers: 'always',
    canActivate: [UserGuard],
    component: SuccessComponent
  },
  {
    path: 'add-money',
    runGuardsAndResolvers: 'always',
    canActivate: [UserGuard],
    component: MoneyComponent
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
    path: 'ch_pass',
    component: ChangePasswordComponent
  },
  {
    path: 'my_certs',
    runGuardsAndResolvers: 'always',
    canActivate: [UserGuard],
    component: CertificatesOfUserComponent
  },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];