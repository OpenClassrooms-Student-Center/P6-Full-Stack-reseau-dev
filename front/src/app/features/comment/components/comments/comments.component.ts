import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Comment} from "../../interfaces/comment.interface";
import {BehaviorSubject, Observable, Subscription} from "rxjs";
import {Post} from "../../../post/interfaces/post.interface";

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss']
})
export class CommentsComponent implements OnInit, OnDestroy {

  @Input() post$!: Observable<Post>;

  private commentsSubject = new BehaviorSubject<Comment[]>( []);
  public comments$ = this.commentsSubject.asObservable();

  private postSubscription!: Subscription;

  constructor() { }

  ngOnInit(): void {
    if(this.post$){
      this.postSubscription = this.post$.subscribe({
        next: (post: Post) => {
          if (post.comments) {
            console.log(post.comments);
            this.commentsSubject.next(post.comments);
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

  public handleCommentAdded(comments: Comment[]): void {
    this.commentsSubject.next(comments);
  }

}
