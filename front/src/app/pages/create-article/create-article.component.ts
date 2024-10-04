import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Themes } from 'src/app/interfaces/themes.interface';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit, OnDestroy {
  articleForm: FormGroup;
  themes: Themes[] = [];
  private themesSubscription: Subscription | undefined;
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private snackbarService: SnackbarService,
    private router: Router
  ) {
        this.articleForm = this.formBuilder.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      description: ['']
    });
  }
  ngOnInit(): void {
    this.fetchThemes();
  }
  fetchThemes(): void  {
    this.themesSubscription = this.http.get<{ themes: Themes[] }>('/api/themes')
      .subscribe(
        (response) => {
          this.themes = response.themes; 
        },
        (error) => {
          console.error('Erreur lors de la récupération des thèmes:', error);
        }
      );
  }
  onSubmit(): void  {
    if (this.articleForm.valid) {
      const articleData = {
        title: this.articleForm.get('title')?.value,
        description: this.articleForm.get('description')?.value,
        theme: this.articleForm.get('theme')?.value
      };
      this.http.post('/api/articles', articleData)
        .subscribe(
          (response: any) => { 
            this.snackbarService.openSnackBar('Article créé avec succès', 'Fermer');
            this.router.navigate(['/article']);
            
          },
          (error: any) => { 
            console.error('Erreur lors de la création de l\'article:', error);
          }
        );
      } else {
        this.snackbarService.openSnackBar('Le formulaire est invalide', 'Fermer');
      }
    }
    ngOnDestroy(): void {
      if (this.themesSubscription) {
        this.themesSubscription.unsubscribe();    }
}
}