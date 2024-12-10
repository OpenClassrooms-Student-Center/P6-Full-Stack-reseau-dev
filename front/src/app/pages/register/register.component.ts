import {Component, inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {UserRegisterModel} from "../../models/UserRegisterModel";
import {BackComponent} from "../../components/back/back.component";
import {Router} from "@angular/router";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    MatButton,
    ReactiveFormsModule,
    BackComponent
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  fb:FormBuilder = inject(FormBuilder);
  userService:UserService = inject(UserService);
  router:Router = inject(Router);
  sessionService:SessionService = inject(SessionService);
  /**
   * FormGroup representing the registration form with fields for username, email, and password.
   * Includes validation:
   * - username: required
   * - email: required and must be a valid email format
   * - password: required, must be at least 8 characters long, and contain at least one uppercase letter, one lowercase letter, one number, and one special character.
   */
  public form: FormGroup = this.fb.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$')]],
  });
  /**
   * Handles the registration process by validating the form and submitting the data if valid.
   * If the form is invalid, it marks all fields as touched to show validation errors.
   * On successful registration, logs in the user with the received session data.
   */
  register(): void{
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const userRegister: UserRegisterModel = this.form.value as UserRegisterModel;
    this.userService.register(userRegister).subscribe( result =>{
      this.sessionService.logIn(result)
    }, error => console.log(error));
  }
}
