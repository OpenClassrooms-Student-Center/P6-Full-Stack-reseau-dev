import { Component, OnInit } from '@angular/core';
import { Article } from '../../../interface/article';
import { Observable } from 'rxjs';
import { ArticleService } from '../../../service/article.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  public articles$: Observable<Article[]> = this.articleService.all();

  constructor(
    private articleService : ArticleService,
    private router : Router
  ) { }

  ngOnInit(): void {
    this.articles$ = this.articleService.all();
  }

  create() {
    this.router.navigateByUrl('/article');
  }

}
