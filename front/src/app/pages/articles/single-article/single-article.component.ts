import {Component, inject, Input} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BackComponent} from "../../../components/back/back.component";
import {ArticleService} from "../../../services/article.service";
import {ArticleModel} from "../../../models/ArticleModel";
import {DatePipe} from "@angular/common";
import {MatCard} from "@angular/material/card";
import {FormsModule} from "@angular/forms";
import {CommentModel} from "../../../models/CommentModel";
import {CommentDto} from "../../../models/CommentDto";

@Component({
  selector: 'app-single-article',
  standalone: true,
  imports: [
    BackComponent,
    DatePipe,
    MatCard,
    FormsModule
  ],
  templateUrl: './single-article.component.html',
  styleUrl: './single-article.component.scss'
})
export class SingleArticleComponent {
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  articleService: ArticleService = inject(ArticleService);
  newComment: string = "";
  articleId!: number;
  article!: ArticleModel;
  ngOnInit(): void {
    this.articleId = Number(this.activatedRoute.snapshot.paramMap.get('id')!);
    this.articleService.getSingleArticle(this.articleId).subscribe((result: ArticleModel) => {
      this.article = result
    })
  }
  /**
   * Sends a new comment for the current article to the server.
   * The comment content is taken from the `newComment` variable and sent as a `CommentDto`.
   * Upon successful submission, the article is updated with the latest data (including the new comment).
   */
  sendComment(){
    let comment: CommentDto= {content: this.newComment}
    this.articleService.addComment(this.article.id, comment).subscribe((result: ArticleModel) => {
      this.article = result;
    })
  }
}
