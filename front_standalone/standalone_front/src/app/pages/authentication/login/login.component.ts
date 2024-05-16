import {Component} from '@angular/core';
import {TranslocoModule} from "@jsverse/transloco";
import {MatButton} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    TranslocoModule,
    MatButton,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  usernameControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', Validators.required);
  submitted: boolean = false;
  loginForm = new FormGroup({
    username: this.usernameControl,
    password: this.passwordControl,
  });

  hasAuthError: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
  ) {
  }

  get username() {
    return this.loginForm.get('username')?.value;
  }
  get password() {
    return this.loginForm.get('password')?.value;
  }

  login() {
    this.submitted = true;
    if (this.isValid()) {
      this.authService.login({
        username: this.username ? this.username : '',
        password: this.password ? this.password : '',
      }).subscribe({
        next:(r) => {console.log("RESULT : ", r); this.handleAuthSuccess()},
        error:(err) => this.handleAuthError(err)}
      );
    }
  }

  isValid() {
    return !this.hasError('username') && !this.hasError('password');
  }

  hasError(field: string) {
    return this.submitted && !this.loginForm.get(field)?.valid;
  }

  handleChange() {
    this.hasAuthError = false;
  }

  handleAuthError(err: any) {
    this.hasAuthError = true;
    console.log("LOG ERROR : ", err);
  }

  handleAuthSuccess() {
    this.hasAuthError = false;
    this.router.navigate(['home']);
  }
}
