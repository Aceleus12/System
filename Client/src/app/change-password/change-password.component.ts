import { Component, OnInit } from '@angular/core';
import { AlertifyService } from '../_services/alertify.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  password: string;
  confPassword: string;
  constructor(private alertify: AlertifyService) { }

  ngOnInit() {
  }

  change() {
    this.alertify.success("Hasło zmieniono pomyślnie.");
  }

}
