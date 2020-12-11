import {
  Component,
  EventEmitter,
  HostListener,
  Input,
  OnInit,
  Output,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Certificate } from '../_models/certificate';
import { CertificateToAdd } from '../_models/certificateToAdd';
import { StepToAdd } from '../_models/stepToAdd';
import { CertificateService } from '../_services/certificate.service';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.css'],
})
export class CertificatesComponent implements OnInit {
  @ViewChild('editForm', { static: true }) editForm: NgForm;
  @Input() isEditable = true;
  @Output() getCertificateId = new EventEmitter<string>();
  certificates: Certificate[];
  certificate: CertificateToAdd;
  certificateForModal: Certificate;
  steps: StepToAdd[];
  keyWord = '';

  addCertificateForm: FormGroup;
  modalRef: BsModalRef;

  @HostListener('window:beforeunload', ['$event'])
  unloadNotification($event: any) {
    if (this.editForm.dirty) {
      $event.returnValue = true;
    }
  }

  constructor(
    private certificateService: CertificateService,
    private fb: FormBuilder,
    private modalService: BsModalService
  ) {}

  ngOnInit() {
    this.steps = [];
    this.createAddCertificateForm();
    this.getCertificates();
  }

  createAddCertificateForm() {
    this.addCertificateForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      certDescription: ['', Validators.required],
      nameStep: ['', Validators.required],
    });
  }

  getCertificates() {
    this.certificateService.getCertificates().subscribe(
      (data) => {
        this.certificates = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  addCertificate() {
    debugger;
    this.certificate = {
      name: this.addCertificateForm.get('name').value,
      certificateSteps: this.steps,
      description: this.addCertificateForm.get('certDescription').value
    };

    this.certificateService.addCertificate(this.certificate).subscribe(
      (next) => {
        console.log('success');
        this.getCertificates();
        this.addCertificateForm.reset();
        this.steps = [];
      },
      (error) => {
        console.log('error');
      }
    );
  }

  addStep() {
    const stepToAdd = {
      description: this.addCertificateForm.get('description').value,
      name: this.addCertificateForm.get('nameStep').value,
    };

    this.steps.push(stepToAdd);
    this.addCertificateForm.get('description').reset();
    this.addCertificateForm.get('nameStep').reset();
  }

  deleteStep(id: number) {
    this.steps.splice(id, 1);
  }

  openCertificateDetails(cert: Certificate, template: TemplateRef<any>) {
    this.certificateForModal = cert;
    this.modalRef = this.modalService.show(template);
  }

  deleteCert() {
    this.certificateService
      .deleteCertificate(this.certificateForModal.id)
      .subscribe(
        (next) => {
          this.getCertificates(); 
          this.modalRef.hide();
        },
        (error) => {
          console.log(error);
        }
      );
  }

  onKey(event) {
    debugger;
    this.keyWord = event.target.value;
    if (this.keyWord.length === 0) {
      this.getCertificates();
    } else {
      this.getFiltered();
    }
  }

  getFiltered() {
    this.certificateService.getFilteredCerts(this.keyWord).subscribe(
      (data) => {
        this.certificates = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  beginProcedure() {
    this.getCertificateId.emit(this.certificateForModal.id);
    this.modalRef.hide();
  }
}
