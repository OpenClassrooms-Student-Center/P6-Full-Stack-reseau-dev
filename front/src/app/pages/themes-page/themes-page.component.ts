import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
interface Theme {
  id: number;
  title: string;
  description: string;
  createdAt: string | null;
  updatedAt: string | null;
}
@Component({
  selector: 'app-themes-page',
  templateUrl: './themes-page.component.html',
  styleUrls: ['./themes-page.component.scss']
})
export class ThemesPageComponent implements OnInit {
  themes: Theme[] = [];
  constructor(private http: HttpClient) { }
  ngOnInit(): void {
    this.fetchThemes();
  }
  fetchThemes() {
    this.http.get<{themes: Theme[]}>('/api/themes')
      .subscribe(response => {
        this.themes = response.themes;
      });
  }
  subscribeToTheme(themeId: number) {
    this.http.post<any>('/api/auth/subscribe/' + themeId, {})
      .subscribe(response => {
        // Traiter la réponse de l'API si nécessaire
        console.log('Souscrit avec succès au thème avec l\'ID', themeId);
      });
  }
}