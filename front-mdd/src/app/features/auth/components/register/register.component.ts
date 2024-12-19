import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../interfaces/register-request.interface';
import { AuthService } from '../../services/auth.service';
import { SessionService } from '../../../../shared/services/session.service';
import { passwordValidator } from '../../auth.module';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss',
    standalone: false
})
export class RegisterComponent implements OnInit {
  public onError = false;
  public form!:FormGroup ;
  constructor(private fb: FormBuilder, private router: Router,private authService:AuthService,
    private sessionService:SessionService
  ) {}
public ngOnInit(): void {

/*  this.myGroup = new FormGroup({
    firstName: new FormControl()
});*/
  this.form = this.fb.group({

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
        passwordValidator,
      ]
    ]
  });

}

  onSubmit(): void {
    console.log('register request ...')
    if (this.form.valid || true) {
      console.log('Form Submitted', this.form.value);
      const registerRequest = this.form.value as RegisterRequest;
     this.authService.register(registerRequest).subscribe({
      next: () => this.router.navigate(['/auth/login']),
      error: () => this.onError = true,
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/']); // Retourne à la page précédente ou d'accueil
  }

}