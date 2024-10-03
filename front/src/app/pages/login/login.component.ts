import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  formData: any = {};

  constructor(private http: HttpClient,private router: Router) {}

  ngOnInit(): void {}
  login() {
    this.http.post<any>('/api/auth/login', this.formData)
      .subscribe((response) => {
        if (response && response.token) {
          console.log('Connexion réussie ! Token:', response.token);
          localStorage.setItem('token', response.token);
          this.router.navigate(['/article']);
        } else {
          console.error('Erreur lors de la connexion :', response);
        }
          }, (error) => {
        console.error('Erreur lors de la connexion :', error);
        alert(error.error);
        location.reload();
      });
  }

}