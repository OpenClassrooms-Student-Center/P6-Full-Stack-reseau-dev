import {Component, Input, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect, MatSelectTrigger} from "@angular/material/select";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {TranslocoPipe} from "@jsverse/transloco";
import {PostToDisplay} from "../../../core/models/post";
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-articles-layout',
  standalone: true,
  imports: [
    MatButton,
    MatCard,
    MatCardContent,
    MatCardSubtitle,
    MatCardTitle,
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    MatSelectTrigger,
    NgForOf,
    NgIf,
    TranslocoPipe,
    ReactiveFormsModule,
    DatePipe
  ],
  templateUrl: './articles-layout.component.html',
  styleUrl: './articles-layout.component.scss'
})
export class ArticlesLayoutComponent implements OnInit, OnDestroy{

  @Input() posts: PostToDisplay[] = [];

  sortings = ["date", "title", "theme"];

  formSorting = new FormControl('date');

  subscriptions: Subscription[] = []

  constructor(
    private router: Router,
    public sanitizer: DomSanitizer
    ) {
  }

  ngOnInit(): void {

    this.sortPosts();
    this.subscriptions.push( this.formSorting.valueChanges.subscribe(val => {
      this.sortPosts();
    }));
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
          return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
        case 'title':
          return a.title.localeCompare(b.title);
        case 'theme':
          return a.topicName.localeCompare(b.topicName);
        default:
          throw new Error(`Unknown sorting method "${this.formSorting.value}"`);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe())
  }
}
