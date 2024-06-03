import {Component, OnInit} from '@angular/core';
import {TranslocoPipe} from "@jsverse/transloco";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../../core/models/post";
import {PostService} from "../../../core/services/post.service";
import {Comment, CommentToDisplay, NewComment} from "../../../core/models/comment";
import {MddUserService} from "../../../core/services/mdd-user.service";
import {TopicService} from "../../../core/services/topic.service";
import {CommonModule} from "@angular/common";
import {MatCard, MatCardContent, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {CommentFormComponent} from "../../../shared/component/comment-form/comment-form.component";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CommentService} from "../../../core/services/comment.service";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {MatSuffix} from "@angular/material/form-field";
import {CommentComponent} from "./comment/comment.component";
import {forkJoin} from "rxjs";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    TranslocoPipe, CommonModule, MatCard, MatCardContent, MatCardSubtitle, MatCardTitle, CommentFormComponent, FormsModule, ReactiveFormsModule, MatIcon, MatIconButton, MatSuffix, CommentComponent
  ],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss'
})
export class ArticleComponent implements OnInit{

  post!: Post;

  comments!: CommentToDisplay[];

  authorName = "Author"

  topicName = "Topic"

  postTitle = "Titl"

  loadedData = false;


  commentTextFormControl = new FormControl('', [Validators.required, Validators.maxLength(144)]);
  commentForm = new FormGroup({
    commentText: this.commentTextFormControl,
  });
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private postService: PostService,
    public mddUserService: MddUserService,
    public topicService: TopicService,
    private commentService: CommentService,
    public sanitizer: DomSanitizer
  ) {
  }
  ngOnInit(): void {
    const postId = this.route.snapshot.params['id'];
    this.postService.getPostById(postId).subscribe({
      next: res => {
        this.post = res;
        console.log("POST : ", res);
        this.postTitle = res.title;
        if(this.post.topicId && this.post.authorId) {
          forkJoin([this.mddUserService.getUserById(this.post.authorId), this.topicService.getTopic(this.post.topicId)])
            .subscribe({
              next: res => {
                this.authorName = res[0].username;
                this.topicName = res[1].name;
                this.loadedData = true;
              }
            })
          this.mddUserService.getUserById(this.post.authorId).subscribe()
        }
      }
    })
    this.getComments(postId);
  }
  goBack(){
    this.router.navigate(['/home'])
  }

  getComments(postId: number){
    this.commentService.commentByPost(postId).subscribe({
      next: comments => {
        console.log("Comments : ", comments)
        this.comments = comments;
      },
      error: err => {
        console.log('comments error : ', err)}
    })
  }

  SendComment(){
    const newComment: NewComment = {

      comment: this.commentForm.controls.commentText.value ? this.postService.swapEndOfLineForHtmlTag(this.commentForm.controls.commentText.value) : "",
      postId: this.post.id ? this.post.id : -1,
    }
    this.commentService.newComment(newComment).subscribe({
      next: message => { this.getComments(this.post.id ? this.post.id : -1) }
    })
  }


}
