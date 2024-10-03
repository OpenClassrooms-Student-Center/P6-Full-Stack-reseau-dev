import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {
  articleForm: FormGroup;
  themes: any[] = [];
  constructor(private formBuilder: FormBuilder, private http: HttpClient) {
    this.articleForm = this.formBuilder.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      description: ['']
    });
  }
  ngOnInit(): void {
    this.fetchThemes();
  }
  fetchThemes() {
    this.http.get<any>('/api/themes')
      .subscribe(
        (response: any) => {
          this.themes = response.themes; 
        },
        (error: any) => {
          console.error('Erreur lors de la récupération des thèmes:', error);
        }
      );
  }
  onSubmit() {
    if (this.articleForm.valid) {
      console.log('title', this.articleForm.get('title')?.value);
      console.log('description', this.articleForm.get('description')?.value);
      console.log('theme', this.articleForm.get('theme')?.value);
      const articleData = {
        title: this.articleForm.get('title')?.value,
        description: this.articleForm.get('description')?.value,
        theme: this.articleForm.get('theme')?.value
      };
      this.http.post<any>('/api/articles', articleData)
        .subscribe(
          (response: any) => { 
            console.log('Article créé avec succès:', response);
          },
          (error: any) => { 
            console.error('Erreur lors de la création de l\'article:', error);
          }
        );
      } else {
        console.log('Le formulaire est invalide');
    }
}
}