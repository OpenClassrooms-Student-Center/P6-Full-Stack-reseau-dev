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

  @Input() post$!: Observable<Post>;

  private commentsSubject = new BehaviorSubject<Comment[]>( []);
  public comments$ = this.commentsSubject.asObservable();

  constructor(private commentService: CommentService) { }

  ngOnInit(): void {
    if(this.post$){
      this.post$.subscribe({
        next: (post: Post) => {
          if(post.comments){
            console.log(post.comments)
            this.commentsSubject.next(post.comments);
          }
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }

  public handleCommentAdded(comments: Comment[]): void {
    this.commentsSubject.next(comments);
  }

}
