import { Component, ElementRef, OnInit, TemplateRef, ViewChild, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Certificate } from '../_models/certificate';
import { StepToReturn } from '../_models/stepToReturn';
import { User } from '../_models/user';
import { CertificateService } from '../_services/certificate.service';
import { GainedStepsService } from '../_services/gained-steps.service';
import { StepService } from '../_services/step.service';
import { UsersService } from '../_services/users.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  @ViewChildren('fileInput') fileInput: ElementRef;

  user: User;
  certificates = [];
  unconfirmedCertificates = [];
  confirmedCertificates = [];
  modalRef: BsModalRef;
  step: StepToReturn;
  file: any;
  comment = '';
  idk: any;
  gainedId: any;

  constructor(
    private route: ActivatedRoute,
    private certificateService: CertificateService,
    private userService: UsersService,
    private stepService: StepService,
    private modalService: BsModalService,
    private gainedStepsService: GainedStepsService
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
    this.modalRef = this.modalService.show(template, { class: 'modal-lg' });
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

  getUnconfirmed(id: string, template: TemplateRef<any>) {
    this.gainedId = id;
    this.gainedStepsService.getUncofrimedForUser(id, this.user.id).subscribe(
      (data) => {
        if(data.length > 0) {
          this.step = data[0];
          this.modalRef = this.modalService.show(template, { class: 'modal-lg' });
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      this.file = event.target.files[0];
    }
  }

  addFile(id: string) {
    const formData = new FormData();
    formData.append('file', this.file);
    this.stepService.addFile(id, formData).subscribe(
      (next) => {
        this.reloadSteps();
      },
      (error) => {
        console.log('error');
      }
    );
  }

  addComment(id: string) {
    this.stepService.addComment(id, this.comment).subscribe(
      (next) => {
        console.log('success');
        this.reloadSteps();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  downloadFile(id: string) {
    window.open('http://localhost:8080/api/file/' + id);
  }

  confirmStep(id: string) {
    this.stepService.confirmStep(id).subscribe(
      (next) => {
        this.getAllCertificates();
        this.reloadSteps();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  reloadSteps() {
    this.gainedStepsService
      .getUncofrimedForUser(this.gainedId, this.user.id)
      .subscribe(
        (data) => {
          if (data.length > 0) {
            this.step = data[0];
            this.comment = '';
          } else {
            this.modalRef.hide();
          }
        },
        (error) => {
          console.log(error);
        }
      );
  }

  
}

