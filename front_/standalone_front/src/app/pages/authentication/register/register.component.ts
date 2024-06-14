import { Component } from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {TranslocoModule} from "@jsverse/transloco";
import {AuthService} from "../../../core/services/auth.service";
import {Router} from "@angular/router";
import {ToasterService} from "../../../core/services/toaster.service";
import {passwordValidator} from "../../../shared/validators/validators.functions";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    TranslocoModule,
    MatError,
    NgIf,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  usernameControl = new FormControl('', Validators.required);
  emailControl = new FormControl('', [Validators.required, Validators.email]);
  passwordControl = new FormControl('', [Validators.required, passwordValidator()]);
  passwordMatchControl = new FormControl('', [Validators.required]);

  submitted: boolean = false;
  registerForm = new FormGroup({
    username: this.usernameControl,
    email: this.emailControl,
    password: this.passwordControl,
    passwordMatch: this.passwordMatchControl,
  });
  hasRegisterError:boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private toasterService: ToasterService,
  ) {}

  register(){
    this.submitted = true;
    if (this.isValid()) {
      this.authService.register({
        username: this.username ? this.username : '',
        email: this.email ? this.email : '',
        password: this.password ? this.password : '',
      }).subscribe({
        next:(r) => {this.handleAuthSuccess()},
        error:(err) => this.handleAuthError(err)}
      );
    } else {
      this.toasterService.handleWarning("Passwords tipingd don't match");
    }
  }

  isValid() {
    return this.registerForm.valid && this.matches(this.registerForm);
  }


  handleAuthSuccess() {
    this.hasRegisterError = false;
    this.toasterService.handleSuccess('Registered successfully')
    this.router.navigate(['login']);
  }

  handleAuthError(err: any) {
    this.toasterService.handleError(err)
    this.hasRegisterError = true;
  }

  goToHome(){
    this.router.navigate(['home']);
  }

  hasError(field: string) {
    const has =
      this.submitted && !this.registerForm.get(field)?.valid;
    if(field === "passwordMatch"){
      return  has && !this.matches(this.registerForm);
    }
    return has;
  }

  handleChange() {
    this.hasRegisterError = false;
  }

  matches(form: AbstractControl){
    return form.get('password')?.value === form.get('passwordMatch')?.value;
  }

  get email() {
    return this.registerForm.get('email')?.value;
  }
  get password() {
    return this.registerForm.get('password')?.value;
  }

  get username() {
    return this.registerForm.get('username')?.value;
  }
}
