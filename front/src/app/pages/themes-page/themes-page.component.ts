import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Themes } from 'src/app/interfaces/themes.interface';



@Component({
  selector: 'app-themes-page',
  templateUrl: './themes-page.component.html',
  styleUrls: ['./themes-page.component.scss']
})
export class ThemesPageComponent implements OnInit {
  themes: Themes[] = [];
  constructor(private http: HttpClient,private _snackBar: MatSnackBar) { }
  ngOnInit(): void {
    this.fetchThemes();
  }
  fetchThemes(): void  {
    this.http.get<{themes: Themes[]}>('/api/themes')
      .subscribe(response => {
        this.themes = response.themes;
      });
  }
  subscribeToTheme(themeId: number): void  {
    this.http.post<Themes[]>('/api/auth/subscribe/' + themeId, {})
      .subscribe(response => {
        // Traiter la réponse de l'API si nécessaire
        this.openSnackBar('Souscrit avec succès au thème !', 'Fermer');
      });
  }
  openSnackBar(message: string, action: string): void  {
    this._snackBar.open(message, action);
  }
}