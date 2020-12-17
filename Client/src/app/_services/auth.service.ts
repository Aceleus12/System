import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from '../_models/user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  baseUrl = environment.apiUrl;
  jwtHelper = new JwtHelperService();
  decodedToken: any;
  roles: string[];
  userId: string;
  username: string;
  isAdmin: boolean;

  constructor(private http: HttpClient, private router: Router) {}

  signIn(model: any) {
    return this.http.post(this.baseUrl + 'user/sign_in', model).pipe(
      map((response: any) => {
        const res = response;
        debugger;
        if (res) {
          debugger;
          this.decodedToken = this.jwtHelper.decodeToken(res.token);
          this.roles = res.roles;
          this.userId = res.id;
          this.username = res.username;
          localStorage.setItem('token', res.token);
          localStorage.setItem('userId', res.id);
          this.isAdmin = this.roles.some((e) => e === 'ROLE_ADMIN');
          if (this.isAdmin) {
            localStorage.setItem('role', 'admin');
            this.router.navigate(['/users']);
          } else {
            localStorage.setItem('role', 'user');
            this.router.navigate(['/my_certs']);
          }
        }
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    this.decodedToken = null;
    this.roles = [];
    this.userId = '';
    this.username = '';
    this.isAdmin = false;
    this.router.navigate(['/login']);
  }

  loggedIn(): boolean {
    if (localStorage.getItem('token')) {
      return true;
    } else {
      return false;
    }
  }

  isAdminLogged() {
    if (localStorage.getItem('role') === 'admin') {
      return true;
    } else {
      return false;
    }
  }
}
