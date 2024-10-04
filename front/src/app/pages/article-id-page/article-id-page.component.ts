import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Themes } from 'src/app/interfaces/themes.interface';
import { Article } from 'src/app/interfaces/article.interface';
import { Subscription } from 'rxjs';

interface FormData {
  message: string;
}

@Component({
  selector: 'app-article-id-page',
  templateUrl: './article-id-page.component.html',
  styleUrls: ['./article-id-page.component.scss']
})
export class ArticleIdPageComponent implements OnInit, OnDestroy {
  article: Article | undefined;
  formData: FormData = {
    message: ''
  };
  private articleSubscription: Subscription | undefined;

  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router, private snackbarService: SnackbarService) { }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.fetchArticle(id);
      }
    });
  }
  ngOnDestroy(): void {
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }
  postMessage(id: number) {
    this.http.post(`/api/articles/${id}/messages`, this.formData)
      .subscribe((response) => {
        this.router.navigate([`/article/${id}`]);
        location.reload();
      }, (error) => {
        this.snackbarService.openSnackBar("Erreur lors de l'inscription :", "fermer");
      });
  }

  fetchArticle(id: number) {
    this.articleSubscription = this.http.get<Article>(`/api/articles/${id}`)
      .subscribe(
        (response) => {
          this.article = response;
        },
        (error) => {
          this.snackbarService.openSnackBar('Erreur lors de la récupération des articles :', "fermer");
        }
      );
  }
}