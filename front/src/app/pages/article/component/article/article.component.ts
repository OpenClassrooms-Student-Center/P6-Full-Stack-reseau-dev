import { Component, OnInit } from '@angular/core';
import { Article } from '../../interface/article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ArticleService } from '../../service/article.service';
import { SessionService } from 'src/app/services/session.service';
import { ThemeService } from 'src/app/pages/theme/service/theme.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  public articleForm: FormGroup | undefined;

  // a faire seulement pour abonnements
  public themes$ = this.themeService.all();

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private articleService: ArticleService,
    private sessionService: SessionService,
    private themeService: ThemeService,
    private router: Router
  ) {

   }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(article?: Article): void {
    this.articleForm = this.fb.group({
      title: [
        article ? article.title : '',
        [Validators.required]
      ],
      theme_id: [
        article ? article?.theme_id : '',
        [Validators.required]
      ],
      description: [
        article ? article.contenu : '',
        [
          Validators.required,
          Validators.max(2000)
        ]
      ],
    });
  }
  public submit(): void {
    const article = this.articleForm?.value as Article;
      this.articleService
        .create(article)
        .subscribe((_: Article) => this.exitPage('Session created !'));
  }

  private exitPage(message: string): void {
    this.matSnackBar.open(message, 'Close', { duration: 3000 });
    this.router.navigate(['themes']);
  }
}
