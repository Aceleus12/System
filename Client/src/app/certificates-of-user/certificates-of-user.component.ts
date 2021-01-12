import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Certificate } from '../_models/certificate';
import { User } from '../_models/user';
import { AlertifyService } from '../_services/alertify.service';
import { AuthService } from '../_services/auth.service';
import { CertificateService } from '../_services/certificate.service';
import { GainedStepsService } from '../_services/gained-steps.service';
import { StepService } from '../_services/step.service';
import { UsersService } from '../_services/users.service';

@Component({
  selector: 'app-certificates-of-user',
  templateUrl: './certificates-of-user.component.html',
  styleUrls: ['./certificates-of-user.component.css'],
})
export class CertificatesOfUserComponent implements OnInit {
  certificates: any[];
  myUserId: string;
  gainedId: any;
  step: any;
  file: any = null;
  modalRef: BsModalRef;
  selectedCertificate: any;
  selectedGainedCertificate: any;

  constructor(
    private userService: UsersService,
    private authService: AuthService,
    private gainedStepsService: GainedStepsService,
    private certificateService: CertificateService,
    private alertifyService: AlertifyService,
    private stepService: StepService,
    private modalService: BsModalService
  ) {}

  ngOnInit() {
    this.myUserId = localStorage.getItem('userId');
    this.getAllCertificates();
  }

  getAllCertificates() {
    this.userService.getCertificatesForUser(this.myUserId).subscribe((data) => {
      debugger;
      this.certificates = data;
    });
  }

  getUnconfirmed(id: string, certID: string, template: TemplateRef<any>) {
    this.gainedId = id;
    this.gainedStepsService.getUncofrimedForUser(id, this.myUserId).subscribe(
      (data) => {
        if (data.length > 0) {
          this.getDetailsOfCertificate(certID);
          this.step = data[0];
          debugger;
          this.modalRef = this.modalService.show(template, {
            class: 'modal-lg',
          });
          this.modalRef.onHide.subscribe((next) => {
            this.file = null;
          })
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  getDetailsOfCertificate(id: string) {
    this.certificateService.getCertificate(id).subscribe(
      (data) => {
        debugger;
        this.selectedCertificate = data;
      },
      (error) => {
        this.alertifyService.error(error);
      }
    );
  }

  addFile(id: string) {
    debugger;
    const formData = new FormData();
    formData.append('file', this.file);
    this.stepService.addFileFromUser(id, formData).subscribe(
      (next) => {
        //this.reloadSteps();
        this.alertifyService.success('Dodano');
      },
      (error) => {
        this.alertifyService.error(error);
      }
    );
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      this.file = event.target.files[0];
    }
  }

  downloadFile(id: string) {
    debugger;
    this.stepService.downloadFile(id);
  }

  openDetails(template: TemplateRef<any>) {
    this.modalRef.hide();
    this.modalRef = this.modalService.show(template, { class: 'modal-lg' });
  }

  openFinished(gainedID: string, template: TemplateRef<any>) {
    debugger;
    this.stepService.getGainedCertificateForUser(gainedID).subscribe(
      (data) => {
        debugger;
        this.selectedGainedCertificate = data;
        this.modalRef = this.modalService.show(template, {
          class: 'modal-lg',
        });
      },
      (error) => {
        this.alertifyService.error(error);
      }
    );
  }

  getIndex(id: string): number {
    return this.selectedCertificate.certificateSteps.findIndex(x => x.id == id) + 1;
  }

  
}
