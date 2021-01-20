import { Component, OnInit } from '@angular/core';
import { AlertifyService } from '../_services/alertify.service';
import { ChargeService } from '../_services/charge.service';
import { UsersService } from '../_services/users.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {
  user: any;

  constructor(private paymentService: ChargeService, private alertify: AlertifyService, private userService: UsersService) { }

  ngOnInit(): void {
    this.paymentService.addMoney(localStorage.getItem('userId'), +localStorage.getItem('money')).subscribe((data) => {
      this.getUser();
      localStorage.removeItem('money');
    },
    error => {
      this.alertify.error(error);
    })
  }

  getUser() {
    const x: string = localStorage.getItem('userId');
    this.userService.getUser(x).subscribe((data) => {
      debugger;
      this.user = data;
      
    },
    error => {
      this.alertify.error(error);
    })
  }
}
