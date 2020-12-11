import {
  Component,
  HostListener,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { window } from 'rxjs/operators';
import { User } from '../_models/user';
import { StepService } from '../_services/step.service';
import { UsersService } from '../_services/users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  @ViewChild('editForm', { static: true }) editForm: NgForm;
  addUserForm: FormGroup;
  modalRef: BsModalRef;

  user: User;
  userForModal: User;
  users: User[];
  keyWord = '';

  @HostListener('window:beforeunload', ['$event'])
  unloadNotification($event: any) {
    if (this.editForm.dirty) {
      $event.returnValue = true;
    }
  }

  constructor(
    private fb: FormBuilder,
    private userService: UsersService,
    private modalService: BsModalService,
    private stepService: StepService
  ) {}

  ngOnInit() {
    this.createAddUserForm();
    this.getUsers();
  }

  createAddUserForm() {
    this.addUserForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      fatherName: ['', Validators.required],
      pesel: ['', Validators.required],
      email: ['', Validators.required],
    });
  }

  addUser() {
    if (this.addUserForm.valid) {
      this.user = Object.assign({}, this.addUserForm.value);
      this.userService.addUser(this.user).subscribe(
        (next) => {
          console.log('success');
          this.getUsers();
        },
        (error) => {
          console.log(error);
        }
      );
      this.createAddUserForm();
    }
  }

  getUsers() {
    this.userService.getUsers().subscribe((data) => {
      this.users = data;
    });
  }

  openUserDetails(user: User, template: TemplateRef<any>) {
    this.userForModal = user;
    this.modalRef = this.modalService.show(template, {class: 'modal-lg'});
  }

  deleteUser() {
    this.userService.deleteUser(this.userForModal.id).subscribe(
      (next) => {
        this.modalRef.hide();

        this.getUsers();
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
      this.getUsers();
    } else {
      this.getFiltered();
    }
  }

  getFiltered() {
    this.userService.getFilteredUsers(this.keyWord).subscribe(
      (data) => {
        this.users = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
