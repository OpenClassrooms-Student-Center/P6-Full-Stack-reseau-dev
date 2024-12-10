import {Component, inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {Router, RouterLink} from "@angular/router";
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserLoginModel} from "../../models/UserLoginModel";
import {TokenModel} from "../../models/TokenModel";
import {BackComponent} from "../../components/back/back.component";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatButton,
    RouterLink,
    ReactiveFormsModule,
    BackComponent
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  fb:FormBuilder = inject(FormBuilder);
  router:Router = inject(Router);
  userService:UserService = inject(UserService);
  sessionService:SessionService = inject(SessionService);

  public form: FormGroup = this.fb.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.min(3)]]
  });
  /**
   * Handles the user login process by sending the login form data to the server.
   * On successful login, the server's token model is logged and the session is initiated.
   * If there is an error during the login process, the error is logged to the console.
   */
  connexion(): void{
    const userLogin: UserLoginModel = this.form.value as UserLoginModel;
    this.userService.login(userLogin).subscribe((result:TokenModel): void =>{
      this.sessionService.logIn(result)
    }, error => console.log(error));
  }
}
