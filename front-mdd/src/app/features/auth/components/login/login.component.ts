import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from '../../interfaces/login-request';
import { AuthService } from '../../services/auth.service';
import { SessionInformation } from '../../../../shared/interfaces/session-information.interface';
import { SessionService } from '../../../../shared/services/session.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss',
    standalone: false
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  constructor(private fb: FormBuilder, private router: Router,private authService:AuthService,
    private sessionService:SessionService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      identifier: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
    }

  onSubmit(): void {
    if (this.form.valid) {
      console.log('Form Submitted', this.form.value);
      const loginRequest = this.form.value as LoginRequest;
     this.authService.login(loginRequest).subscribe({
        next: (response: SessionInformation) => {
          localStorage.setItem('token', response.token); 

          this.sessionService.logIn(response);
          this.router.navigate(['post/list']);
        },
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/']); // Retourne à la page précédente ou d'accueil
  }
}
