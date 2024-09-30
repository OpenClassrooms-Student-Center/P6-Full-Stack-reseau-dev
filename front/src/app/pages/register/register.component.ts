import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  formData: any = {}; 

  constructor(private http: HttpClient,private router: Router) { }
  ngOnInit(): void {}

  register() {
    console.log('formData:', this.formData);

    this.http.post('http://localhost:8080/api/auth/register', this.formData)
      .subscribe((response) => {
        console.log('Inscription réussie !', response);
        this.router.navigate(['/login']);

      }, (error) => {
        console.error('Erreur lors de l\'inscription :', error);
      });
  }
}