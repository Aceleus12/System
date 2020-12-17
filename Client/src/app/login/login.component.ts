import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  model: any = {};
  constructor(private authService: AuthService) {}

  ngOnInit() {}

  login() {
    debugger;
    this.authService.signIn(this.model).subscribe(
      (next) => {
        console.log('success');
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
