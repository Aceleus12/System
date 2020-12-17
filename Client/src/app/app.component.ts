import { Component } from '@angular/core';
import { AlertifyService } from './_services/alertify.service';
import { AuthService } from './_services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Client';
  constructor(
    public authService: AuthService,
    private alertify: AlertifyService
  ) {}

  logout() {
    this.alertify.confirm('Czy na pewno chcesz się wylogować?', () => {
      this.authService.logout();
    });
  }
}
