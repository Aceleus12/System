import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { User } from '../_models/user';
import { Observable } from 'rxjs';
import { map, repeat, tap } from 'rxjs/operators';
import { Certificate } from '../_models/certificate';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  baseUrl = environment.apiUrl + 'user/';
  constructor(private http: HttpClient) {}

  addUser(user: User) {
    return this.http.post(this.baseUrl, user);
  }

  getUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(this.baseUrl + 'all', { observe: 'response' })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }

  getFilteredUsers(word?: string): Observable<User[]> {
    const params = new HttpParams().set('string', word);
    return this.http
      .get<User[]>(this.baseUrl + 'with_filters', { observe: 'response', params })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }
  
  deleteUser(id: string) {
    return this.http.delete(this.baseUrl + id);
  }

  getUser(id: string): Observable<User> { 
    return this.http.get<User>(this.baseUrl + id);
  }

  getCertificatesForUser(userId: string): Observable<Certificate[]> {
    return this.http
      .get<Certificate[]>(this.baseUrl + 'certificates/' + userId, { observe: 'response' })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }

  getCertificatesUnconfirmedForUser(userId: string): Observable<Certificate[]> {
    return this.http
      .get<Certificate[]>(this.baseUrl + 'certificates/unconfirmed/' + userId, { observe: 'response' })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }

  getCertificatesConfirmedForUser(userId: string): Observable<Certificate[]> {
    return this.http
      .get<Certificate[]>(this.baseUrl + 'certificates/confirmed/' + userId, { observe: 'response' })
      .pipe(
        map((response) => {
          return response.body;
        })
      );
  }
}
