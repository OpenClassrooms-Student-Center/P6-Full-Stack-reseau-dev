import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Comment} from "../../interfaces/comment.interface";
import {CommentService} from "../../services/comment.service";
import {Observable, Subscription} from "rxjs";
import {Post} from "../../../post/interfaces/post.interface";

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.scss']
})
export class NewCommentComponent implements OnInit, OnDestroy {

  @Input() post$: Observable<Post> | null = null;
  @Input() postId: number | null = null;
  @Output() commentAdded = new EventEmitter<Comment[]>();

  private postSubscription!: Subscription;

  public onError = false;
  public errorMessage = "";

  public form = this.fb.group({
    content: [
      '',
      [
        Validators.required
      ]
    ]
  });

  constructor(private fb: FormBuilder,
              private commentService:CommentService) { }

  ngOnInit(): void {
    if(this.post$){
      this.postSubscription = this.post$.subscribe({
        next: (post: Post) => {
          if(post.id){
            console.log(post.id)
            this.postId = post.id;
          }
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }

  ngOnDestroy(): void {
    if (this.postSubscription) {
      this.postSubscription.unsubscribe();
    }
  }

  public submit(): void {
    console.log(this.postId)
    const comment: Comment = {
      content: this.form.value.content??'',
      postId: Number(this.postId),
      user: JSON.parse(sessionStorage.getItem('user') as string)
    }
    this.commentService.create(Number(this.postId), comment).subscribe({
      next: (comments: Comment[]) => {
        this.commentAdded.emit(comments);
        this.form.reset();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

}
