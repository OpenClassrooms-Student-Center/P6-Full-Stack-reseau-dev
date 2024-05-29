import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {PostService} from "../../core/services/post.service";
import {Router} from "@angular/router";
import {Post, PostToDisplay} from "../../core/models/post";
import {ToasterService} from "../../core/services/toaster.service";
import {MatCard, MatCardContent, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {MatButton} from "@angular/material/button";
import {TranslocoPipe} from "@jsverse/transloco";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect, MatSelectTrigger} from "@angular/material/select";
import {NgForOf, NgIf} from "@angular/common";
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {ArticlesLayoutComponent} from "../../shared/component/articles-layout/articles-layout.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    MatCard,
    MatCardTitle,
    MatCardContent,
    MatCardSubtitle,
    MatButton,
    TranslocoPipe,
    MatFormFieldModule,
    MatOption,
    MatSelect,
    MatSelectTrigger,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    ArticlesLayoutComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  public posts: PostToDisplay[] = [];

  constructor(
    private postService: PostService,
    private toasterService: ToasterService,
  ) {
  }

  ngOnInit(): void {
    this.postService.getAllSubscribedTopicPosts().subscribe({
      next: res => {
        this.posts = res;
      },
      error: err => {this.toasterService.handleError(err)}
    });

  }
}
