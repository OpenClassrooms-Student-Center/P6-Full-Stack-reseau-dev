import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from './services/auth.service'; // Assurez-vous que le chemin est correct

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isLoggedIn: boolean = false; // Ajoutez cette propriété pour gérer l'état de connexion

  constructor(private _snackBar: MatSnackBar, private authService: AuthService) {
    this.isLoggedIn = this.authService.isLoggedIn(); // Vérifie si l'utilisateur est connecté
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 1000, 
    });
  }  
}
