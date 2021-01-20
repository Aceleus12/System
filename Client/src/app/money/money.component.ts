import { Component, OnInit } from '@angular/core';
import { AlertifyService } from '../_services/alertify.service';
import { UsersService } from '../_services/users.service';
import {loadStripe} from '@stripe/stripe-js';
import {AfterViewInit, ChangeDetectorRef, ElementRef, Inject, OnDestroy, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { ChargeService } from '../_services/charge.service';

@Component({
  selector: 'app-money',
  templateUrl: './money.component.html',
  styleUrls: ['./money.component.css']
})
export class MoneyComponent implements OnInit {
  stripePromise: any;
  model: any = {};
  user: any;
  session: any;
  constructor(private userService: UsersService, private alertify: AlertifyService, private paymentService: ChargeService) { }

  ngOnInit(): void {
    this.stripePromise = loadStripe(
      'pk_test_51I5YvcJvKXNzTQcriYlZbz2AY6gfE97xSpJ7mUaL2UWOAABOjPBN9mlS3421iRf2nL25anPw6bgSSP5mxg7yI1Z600SJy8Gsm7'
    );
    this.getUser();
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

  charge() {
    debugger;
    this.paymentService.charge(this.model).subscribe(
      async (data) => {
        this.session = data;
        localStorage.setItem('money', this.model.cost);
        const stripe = await this.stripePromise;
        const result = await stripe.redirectToCheckout({
          sessionId: this.session.id,
        });
        
        if(result.error) {
          this.alertify.error(result.error.message);
        } else {
          this.alertify.success('Sukces');
        }
      },
      (error) => this.alertify.error(error)
    );
  }

}

