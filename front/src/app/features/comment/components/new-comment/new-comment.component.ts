import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Comment} from "../../interfaces/comment.interface";
import {CommentService} from "../../services/comment.service";

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.scss']
})
export class NewCommentComponent implements OnInit {

  @Input() postId:Number|null = null;
  @Output() commentAdded = new EventEmitter<Comment[]>();

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
  }

  public submit(): void {
    const comment: Comment = this.form.value as Comment;
    this.commentService.create(Number(this.postId), comment).subscribe({
      next: (comments: Comment[]) => {
        this.commentAdded.emit(comments);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

}
