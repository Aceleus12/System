import { Component, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Certificate } from '../_models/certificate';
import { User } from '../_models/user';
import { CertificateService } from '../_services/certificate.service';
import { StepService } from '../_services/step.service';
import { UsersService } from '../_services/users.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  user: User;
  certificates = [];
  unconfirmedCertificates = [];
  confirmedCertificates = [];
  modalRef: BsModalRef;

  constructor(
    private route: ActivatedRoute,
    private certificateService: CertificateService,
    private userService: UsersService,
    private stepService: StepService,
    private modalService: BsModalService
  ) {}

  ngOnInit() {
    this.route.data.subscribe((data) => {
      this.user = data['user'];
      this.getAllCertificates();
      this.getConfirmedCertificates();
      this.getUnconfirmedCertificates();
    });
  }

  getAllCertificates() {
    this.userService.getCertificatesForUser(this.user.id).subscribe((data) => {
      this.certificates = data;
    });
  }

  getUnconfirmedCertificates() {
    this.userService
      .getCertificatesUnconfirmedForUser(this.user.id)
      .subscribe((data) => {
        this.unconfirmedCertificates = data;
      });
  }

  getConfirmedCertificates() {
    this.userService
      .getCertificatesConfirmedForUser(this.user.id)
      .subscribe((data) => {
        this.confirmedCertificates = data;
      });
  }

  startProcedure(event) {
    debugger;
    this.stepService.startProcedure(event, this.user.id).subscribe(
      (next) => {
        this.modalRef.hide();
        this.getAllCertificates();
        console.log('success');
      },
      (error) => {
        console.log(error);
      }
    );
  }

  openStartProcedure(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template, {class: 'modal-lg'});
  }

  getName(id: string): string {
    let cert;
    this.certificateService.getCertificate(id).subscribe(
      (data) => {
        cert = data;
      },
      (error) => {
        console.log(error);
      }
    );
    return cert.name;
  }

}
