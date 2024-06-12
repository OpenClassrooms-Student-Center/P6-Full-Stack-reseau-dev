import {Component, OnInit} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {TranslocoPipe} from "@jsverse/transloco";
import {MddUserService} from "../../core/services/mdd-user.service";
import {Topic} from "../../core/models/topic";
import {ThemeLayoutComponent} from "../../shared/component/theme-layout/theme-layout.component";
import {TopicService} from "../../core/services/topic.service";
import {AuthService} from "../../core/services/auth.service";
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {ToasterService} from "../../core/services/toaster.service";
import {matchValidator} from "../../shared/validators/validators.functions";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    TranslocoPipe,
    ThemeLayoutComponent,
    NgIf,
    MatError
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  usernameControl = new FormControl('', [Validators.required]);
  mailControl = new FormControl('', [Validators.required, Validators.email]);
  submitted: boolean = false;
  userForm = new FormGroup({
    username: this.usernameControl,
    mail: this.mailControl,
  });

  passwordControl = new FormControl('', Validators.required);
  passwordMatchControl = new FormControl('', [Validators.required, matchValidator(this.passwordControl)]);

  submittedPassword: boolean = false;
  newPasswordForm = new FormGroup({
    password: this.passwordControl,
    passwordMatch: this.passwordMatchControl,
  });
  hasNewPasswordError:boolean = false;




  myTopicsSubscriptions: Topic[] = [];
  isloaded = false

  constructor(
    private mddUserService: MddUserService,
    private topicService: TopicService,
    private authService: AuthService,
    private router: Router,
    private toasterService: ToasterService,
  ) {


  }

  ngOnInit(): void {
        this.mddUserService.getMe().subscribe({next: info => {
          this.usernameControl.setValue(info.username);
          this.mailControl.setValue(info.email);
          this.myTopicsSubscriptions = info.topicsIds;
          this.isloaded = true;
        }})
    }

  handleChange() {

  }

  newUserInfo(){
    if(!this.userForm.valid || !this.userForm.value.mail || !this.userForm.value.username) return
    this.mddUserService.newInfo(
      {
        email: this.userForm.value.mail,
        username: this.userForm.value.username
      }
    ).subscribe()
  }


  unsubscribeFromTopic(topicId: number){
    this.topicService.unsubscribeTopic(topicId).subscribe({
      next: res =>
        { this.mddUserService.getMe().subscribe({
          next: info => {
            this.myTopicsSubscriptions = info.topicsIds;
        },
        error : err => {
            this.toasterService.handleError(err)
        }
        })},
      error: err => {
        this.toasterService.handleError(err)
      }
    });
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/welcome'])
  }

  newPassword(){
    this.submittedPassword = true;
    if (this.isPasswordValid()) {
      this.mddUserService.newPassword({
        newPass: this.password ? this.password : '',
      }).subscribe({
        next:(r) => {this.handleNewPassword(r.message)},
        error:(err) => this.handleNewPasswordError(err)}
      );
    } else {
      this.toasterService.handleWarning("Passwords tipingd don't match");
    }
  }

  isPasswordValid() {
    return this.newPasswordForm.valid && this.matches(this.newPasswordForm);
  }

  matches(form: AbstractControl){
    return form.get('password')?.value === form.get('passwordMatch')?.value;
  }

  get password() {
    return this.newPasswordForm.get('password')?.value;
  }

  handleNewPassword(message: string) {
    this.newPasswordForm.reset();
    this.toasterService.handleSuccess(message);
  }

  handleNewPasswordError(err: any) {
    this.toasterService.handleError(err)
  }

  handlePasswordTouched() {
    this.hasNewPasswordError = false;
    this.submittedPassword = false;
  }

}
