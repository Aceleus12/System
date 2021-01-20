import { Component, OnInit } from '@angular/core';
import { ChargeService } from '../_services/charge.service';

@Component({
  selector: 'app-failure',
  templateUrl: './failure.component.html',
  styleUrls: ['./failure.component.css']
})
export class FailureComponent implements OnInit {

  constructor(private paymentService: ChargeService) { }

  ngOnInit(): void {
    localStorage.removeItem('money');
  }

}
