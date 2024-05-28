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
    ReactiveFormsModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit, OnChanges {

  public posts: PostToDisplay[] = [];

  sortings = ["date", "title", "theme"];

  formSorting = new FormControl('date');

  constructor(
    private postService: PostService,
    private router: Router,
    private toasterService: ToasterService,
  ) {
  }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: res => {
        this.posts = res;
        this.sortPosts();
      },
      error: err => {this.toasterService.handleError(err)}
    });

    this.formSorting.valueChanges.subscribe(val => {
      this.sortPosts();
    });
  }

  selectArticle(articleId: number | null) {
    this.router.navigate(['/article/', articleId]);
  }

  createPost() {
    this.router.navigate(['/article/create'])
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  private sortPosts(): void {
    if (!this.posts) {
      return;
    }

    this.posts.sort((a, b) => {
      switch (this.formSorting.value) {
        case 'date':
          return a.createdAt.getTime() - b.createdAt.getTime();
        case 'title':
          return a.title.localeCompare(b.title);
        case 'theme':
          return a.topicName.localeCompare(b.topicName);
        default:
          throw new Error(`Unknown sorting method "${this.formSorting.value}"`);
      }
    });
  }
}
