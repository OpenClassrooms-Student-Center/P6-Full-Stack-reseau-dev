import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
interface Article {
  id: number;
  title: string;
  description: string;
  author: {
    id: number;
    email: string;
    username: string;
    password: string;
    createdAt: Date;
    updatedAt: Date | null;
    themes: Theme[];
  };
  theme: Theme;
  createdAt: Date | string[];
  updatedAt: Date | null;
}
interface Theme {
  id: number;
  title: string;
  description: string;
  createdAt: Date | null;
  updatedAt: Date | null;
}
@Component({
  selector: 'app-article-id-page',
  templateUrl: './article-id-page.component.html',
  styleUrls: ['./article-id-page.component.scss']
})
export class ArticleIdPageComponent implements OnInit {
  article: Article | undefined;
  constructor(private route: ActivatedRoute, private http: HttpClient) { }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.fetchArticle(id); 
      }
    });
  }
  fetchArticle(id: number): void {
    this.http.get<Article>(`http://localhost:8080/api/articles/${id}`)
      .subscribe((response) => {
        response.createdAt = this.convertToDate(response.createdAt);
        this.article = response;
      }, (error) => {
        console.error('Erreur lors de la récupération de l\'article :', error);
      });
  }
  convertToDate(date: Date | string[]): Date {
    if (date instanceof Date) {
      return date; 
    } else if (Array.isArray(date) && date.length === 6) {
      const [year, month, day, hour, minute, second] = date.map(Number);
      return new Date(year, month - 1, day, hour, minute, second);
    } else {
      throw new Error('Format de date non reconnu');
    }
  }
  isValidDate(date: any): boolean {
    return date instanceof Date && !isNaN(date.getTime());
  }
}