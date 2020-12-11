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

@NgModule({
  declarations: [	
    AppComponent,
      UsersComponent,
      CertificatesComponent,
      UserDetailsComponent
   ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    ModalModule.forRoot()
  ],
  providers: [UserDetailResolver],
  bootstrap: [AppComponent]
})
export class AppModule { }
