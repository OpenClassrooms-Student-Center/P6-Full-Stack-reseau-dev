import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Comment } from 'src/app/pages/comment/interface/comment.interface';
import { CommentService } from 'src/app/pages/comment/service/comment.service';
import { ArticleService } from '../../../service/article.service';
import { Article } from '../../../interface/article';
import { Theme } from 'src/app/pages/theme/interface/theme';
import { ThemeService } from 'src/app/pages/theme/service/theme.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public commentForm!: FormGroup;

  public articleId : string;

  public themeId!: string;

  public userId!: string;

  public article! : Article;

  public themeByArticle : Theme | undefined;

  constructor(   
    private articleService: ArticleService, 
    private commentService : CommentService,
    private sessionService : SessionService,
    private matSnackBar: MatSnackBar,
    private fb: FormBuilder,
    private router : Router,
    private route: ActivatedRoute,
    ) {
      this.articleId = this.route.snapshot.paramMap.get('id')!;
     }

  ngOnInit(): void {
    console.log(this.articleId);
    this.articleService.getById(this.articleId).subscribe((article: Article) => 
    { this.article = article
    // this.themeService.getById(article.themeId).subscribe((theme : Theme) => 
    // this.themeByArticle = theme ) 
  });
  // this.themeService.getById(this.themeId).subscribe((theme : Theme) => 
  //   this.themeByArticle = theme); 
    this.initForm();
    
  }


  private initForm(): void {
    this.commentForm = this.fb.group({
      commentaire: ['', [Validators.required, Validators.min(10)]]
    });
  }

  public submit(): void {
    const commentaire = {
      commentaire: this.commentForm?.value.commentaire,
      articleId: this.articleId,
      userId: this.sessionService.sessionInformation?.id.toString()
    } as Comment;
    // const comment = this.commentForm?.value as Comment;
      this.commentService
        .create(commentaire, this.articleId)
        .subscribe((comment: Comment) => {this.exitPage('Commentaire ajouté !'), console.log(comment.commentaire)});
  
      }

  private exitPage(message: string): void {
    this.matSnackBar.open(message, 'Close', { duration: 3000 });
    this.router.navigate(['article/articles']);
  }
  
}
