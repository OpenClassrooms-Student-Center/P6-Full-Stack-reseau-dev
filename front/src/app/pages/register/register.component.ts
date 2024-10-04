import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { RegisterForm } from 'src/app/interfaces/register.interface';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {
  formData: RegisterForm = { email: '', password: '', username: '' };
  private registerSubscription: Subscription | undefined;

  constructor(private http: HttpClient, private router: Router, private snackbarService: SnackbarService) { }
  ngOnInit(): void {}
  register(): void {
    if (!this.validateEmail(this.formData.email)) {
      this.snackbarService.openSnackBar('Email invalide', 'Fermer');
      return;
    }
    if (!this.validatePassword(this.formData.password)) {
      this.snackbarService.openSnackBar('Mot de passe invalide ', 'Fermer');
      return;
    }

    this.registerSubscription = this.http.post('/api/auth/register', this.formData)

         
    .subscribe(
      (response) => {
        this.router.navigate(['/login']);
      },
      (error) => {
        location.reload();
      }
    );
  }
  ngOnDestroy(): void {
    if (this.registerSubscription) {
      this.registerSubscription.unsubscribe();
    }
  }  private validateEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  private validatePassword(password: string): boolean {
    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
    return passwordRegex.test(password);
  }
}