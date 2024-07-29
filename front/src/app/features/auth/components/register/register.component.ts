import {Component, OnInit} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import {AuthService} from "../../services/auth.service";
import {Response} from "../../../../interfaces/response.interface";
import {LoaderService} from "../../../../shared/services/loading.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit{

  public onError = false;
  public errorMessage = "";

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    username: [
      '',
      [
        Validators.required,
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(8),
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:"\\\\|,.<>\\/?]).+$')
      ]
    ]
  });

  constructor(private authService: AuthService,
              private loaderService: LoaderService,
              private fb: FormBuilder,
              private router: Router) {
  }

  ngOnInit() {
    this.loaderService.hide();
  }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
        next: (_: Response) => this.router.navigate(['/login']),
        error: (error) => {
          this.onError = true;
          this.errorMessage = error.error.message;
        }
      }
    );
  }

  public goHome(): void {
    this.router.navigate(['']);
  }
}
