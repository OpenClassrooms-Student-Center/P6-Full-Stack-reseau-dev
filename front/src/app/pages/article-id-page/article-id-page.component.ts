import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface Article {
  id: number;
  title: string;
  description: string;
  username: string;
  messages: {
    id: number,
    userUsername: string,
    message: string,
  }[];
  themes: Theme;
  created_at: String | null;
  updatedAt: Date | null;
}

interface formData{
  message: string;
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
  formData: formData = {
    message: ''
  };
  constructor(private route: ActivatedRoute, private http: HttpClient,private router: Router) { }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.fetchArticle(id); 
      }
    });
  }
  postMessage(id : number){
    console.log(id);
    this.http.post(`/api/articles/${id}/messages`, this.formData)
      .subscribe((response) => {
        console.log('Message posté !', response);
        this.router.navigate([`/article/${id}`]);
        location.reload();
      }, (error) => {
        console.error('Erreur lors de l\'inscription :', error);
      });
  }

  fetchArticle(id: number) {
    this.http.get<Article>(`/api/articles/${id}`)
      .subscribe(
        (response) => {
          this.article = response;
          console.log('this.article', this.article);
        },
        (error) => {
          console.error('Erreur lors de la récupération des articles :', error);
        }
      );
  }
}