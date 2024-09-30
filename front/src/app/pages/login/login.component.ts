import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  formData: any = {};

  constructor(private http: HttpClient) {}
  ngOnInit(): void {}
  login() {
    console.log('formData:', this.formData);
    this.http.post('http://localhost:8080/api/auth/login', this.formData)
      .subscribe((response) => {
        console.log('Connexion réussie !', response);
      }, (error) => {
        console.error('Erreur lors de la connexion :', error);
      });
  }

}