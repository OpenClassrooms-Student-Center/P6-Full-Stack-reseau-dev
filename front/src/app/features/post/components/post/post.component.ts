import { Component, OnInit } from '@angular/core';
import {PostService} from "../../services/post.service";
import {Post} from "../../interfaces/post.interface";
import {ActivatedRoute} from "@angular/router";
import { Location } from '@angular/common';
import {BehaviorSubject} from "rxjs";
import { Comment } from "../../../comment/interfaces/comment.interface";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  id: Number | null = null;

  private postSubject = new BehaviorSubject<Post|null>( null);
  public post$ = this.postSubject.asObservable();

  constructor(
    private postService:PostService,
    private location: Location,
    private route:ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = Number(params['id']);
      this.postService.findById(Number(this.id)).subscribe({
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


}
