import {Component, Input, OnInit} from '@angular/core';
import {CommentService} from "../../services/comment.service";
import {Comment} from "../../interfaces/comment.interface";
import {BehaviorSubject, Observable} from "rxjs";
import {Post} from "../../../post/interfaces/post.interface";

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss']
})
export class CommentsComponent implements OnInit {

  @Input() postId:Number|null = null;

  private commentsSubject = new BehaviorSubject<Comment[]>( []);
  public comments$ = this.commentsSubject.asObservable();

  constructor(private commentService: CommentService) { }

  ngOnInit(): void {
    this.commentService.getCommentsByPostId(Number(this.postId)).subscribe({
      next: (comments: Comment[]) => this.commentsSubject.next(comments),
      error: (error) => {
        console.error(error);
      }
    });
  }

  public handleCommentAdded(comments: Comment[]): void {
    this.commentsSubject.next(comments);
  }

}
