import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {
  
    constructor(@Inject(HttpClient) private http: HttpClient) { }
    themes: any[] = [];
    selectedTheme: number = 0;
    article = {
      title: '',
      content: ''
    };
  
    ngOnInit(): void {
      this.fetchThemes();
    }
    fetchThemes() {
      this.http.get<any[]>('http://localhost:8080/api/themes')
        .subscribe((themes: any[]) => {
          this.themes = themes;
        });
    }
    onSubmit() {
      this.http.post<any>('http://localhost:8080/api/articles', this.article)
        .subscribe(
          (response: any) => { 
            console.log('Article créé avec succès:', response);
          },
          (error: any) => { 
            console.error('Erreur lors de la création de l\'article:', error);
          }
        );
    }
}