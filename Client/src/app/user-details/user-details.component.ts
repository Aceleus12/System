import {
  Component,
  ElementRef,
  OnInit,
  TemplateRef,
  ViewChild,
  ViewChildren,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Certificate } from '../_models/certificate';
import { StepToReturn } from '../_models/stepToReturn';
import { User } from '../_models/user';
import { AlertifyService } from '../_services/alertify.service';
import { AuthService } from '../_services/auth.service';
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
  step: any;
  file: any;
  comment = '';
  idk: any;
  gainedId: any;
  selectedCertificate: any;

  constructor(
    private route: ActivatedRoute,
    private certificateService: CertificateService,
    private userService: UsersService,
    private stepService: StepService,
    private modalService: BsModalService,
    private gainedStepsService: GainedStepsService,
    public authService: AuthService,
    private alertifyService: AlertifyService
  ) {}

  ngOnInit() {
    this.route.data.subscribe((data) => {
      this.user = data['user'];
      debugger;
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
        this.alertifyService.warning('Nie można rozpocząć procedury.');
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
        if (data.length > 0) {
          this.step = data[0];
          debugger;
          this.modalRef = this.modalService.show(template, {
            class: 'modal-lg',
          });
        }
      },
      (error) => {
        console.log(error);
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
    // this will be for user only (for attaching scans/pics or other)
    this.stepService.downloadFile(id);
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



  openAddCertFile(gainedID: string, template: TemplateRef<any>) {
    this.stepService.getGainedCertificateForUser(gainedID).subscribe(
      (data) => {
        this.selectedCertificate = data;
        this
        this.gainedId = gainedID;
        this.modalRef = this.modalService.show(template, { class: 'modal-lg' });
      },
      (error) => {
        this.alertifyService.error(error);
      }
    );
  }
  
  addFile() {
    const formData = new FormData();
    formData.append('file', this.file);
    this.stepService.addFileForGainedCertificate(this.gainedId, formData).subscribe(
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
    debugger;
    if (event.target.files.length > 0) {
      this.file = event.target.files[0];
    }
  }

  confirmCollecting(id: string) {
    this.stepService.confirmCollecting(id).subscribe(
      (next) => {
        this.alertifyService.success('Potwierdzono odebranie.');
        this.modalRef.hide();
        this.getAllCertificates();
      },
      (error) => {
        this.alertifyService.error(error);
      }
    );
  }
}
