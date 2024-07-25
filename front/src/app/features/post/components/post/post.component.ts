import { Component, OnInit } from '@angular/core';
import {PostService} from "../../services/post.service";
import {Post} from "../../interfaces/post.interface";
import {ActivatedRoute} from "@angular/router";
import { Location } from '@angular/common';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  id: Number | null = null;

  post: Post | null = null;

  constructor(
    private postService:PostService,
    private location: Location,
    private route:ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = Number(params['id']);
      this.postService.findById(Number(this.id)).subscribe((post: Post) => {
        this.post = post;
      });
    });
  }

  goBack(): void {
    this.location.back();
  }


}
