import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { UsersComponent } from './users/users.component';
import { CertificatesComponent } from './certificates/certificates.component';
import { RouterModule } from '@angular/router';
import { appRoutes } from './routes';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ModalModule } from 'ngx-bootstrap/modal';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UserDetailResolver } from './_resolvers/user-detail.resolver';
import { LoginComponent } from './login/login.component';
import { JwtModule } from '@auth0/angular-jwt';
import { ErrorInterceptorProvider } from './_services/error.interceptor';
import { CertificatesOfUserComponent } from './certificates-of-user/certificates-of-user.component';
import { AdminGuard } from './_guards/admin.guard';
import { UserGuard } from './_guards/user.guard';

export function tokenGetter() {
  return localStorage.getItem('token');
}

@NgModule({
  declarations: [			
    AppComponent,
      UsersComponent,
      CertificatesComponent,
      UserDetailsComponent,
      LoginComponent,
      CertificatesOfUserComponent
   ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    ModalModule.forRoot(),
    JwtModule.forRoot({
      config: {
        tokenGetter,
        allowedDomains: ['localhost:8080'],
      },
    }),
  ],
  providers: [ErrorInterceptorProvider, UserDetailResolver, AdminGuard, UserGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
