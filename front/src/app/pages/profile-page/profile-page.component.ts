import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {
  user: any; 
  userThemes: any[] = []; 
  updatedUser: any = { username: '', email: '' }; 
  constructor(private http: HttpClient, private router: Router) { }
  ngOnInit(): void {
    this.http.get('http://localhost:8080/api/auth/me').subscribe((data: any) => {
      this.user = data;
      this.updatedUser.username = this.user.username;
      this.updatedUser.email = this.user.email;
    });
    this.http.get('http://localhost:8080/api/auth/themes').subscribe((data: any) => {
      if (Array.isArray(data)) {
        this.userThemes = data;
      } else {
        this.userThemes = [data];
      }
    });
  }
  logout() {
    localStorage.removeItem('token');
    this.router.navigateByUrl('/login');
  }
  UnsubscribeToTheme(themeId: number) {
    this.http.delete<any>('http://localhost:8080/api/auth/subscribe/' + themeId, {})
      .subscribe(response => {
        console.log('Souscrit avec succès au thème avec l\'ID', themeId);
      });
  }
    saveChanges() {
    this.http.put('http://localhost:8080/api/auth/me', this.updatedUser).subscribe((data: any) => {
      console.log('Modifications enregistrées avec succès !');
    });
  }
}