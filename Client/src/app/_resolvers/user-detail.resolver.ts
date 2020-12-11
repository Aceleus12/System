  
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../_models/user';
import { UsersService } from '../_services/users.service';

@Injectable()
export class UserDetailResolver implements Resolve<User> {
  constructor(
    private userService: UsersService,
    private router: Router,
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<User> {
    return this.userService.getUser(route.params['id']).pipe(
      catchError((error) => {
        console.log(error)
        this.router.navigate(['/users']);
        return of(null);
      })
    );
  }
}