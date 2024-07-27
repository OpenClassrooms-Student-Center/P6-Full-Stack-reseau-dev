import { Component, OnInit } from '@angular/core';
import {PostService} from "../../services/post.service";
import {Post} from "../../interfaces/post.interface";
import {ActivatedRoute} from "@angular/router";
import { Location } from '@angular/common';
import {BehaviorSubject, filter, Subscription} from "rxjs";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  id: Number | null = null;

  private postSubject = new BehaviorSubject<Post|null>( null);
  public post$ = this.postSubject.asObservable().pipe(
    filter((post: Post | null): post is Post => post !== null)
  );

  private postSubscription!: Subscription;

  constructor(
    private postService:PostService,
    private location: Location,
    private route:ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = Number(params['id']);
      this.postSubscription = this.postService.findById(Number(this.id)).subscribe({
          next: (post: Post) => this.postSubject.next(post),
          error: (error) => {
            console.error(error);
          }
        }
      );
    });
  }

  goBack(): void {
    this.location.back();
  }

  ngOnDestroy(): void {
    if (this.postSubscription) {
      this.postSubscription.unsubscribe();
    }
  }

}
