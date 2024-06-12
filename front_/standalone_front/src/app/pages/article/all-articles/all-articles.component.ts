import {Component, OnInit} from '@angular/core';
import {PostToDisplay} from "../../../core/models/post";
import {PostService} from "../../../core/services/post.service";
import {ToasterService} from "../../../core/services/toaster.service";
import {ArticlesLayoutComponent} from "../../../shared/component/articles-layout/articles-layout.component";

@Component({
  selector: 'app-all-articles',
  standalone: true,
  imports: [
    ArticlesLayoutComponent
  ],
  templateUrl: './all-articles.component.html',
  styleUrl: './all-articles.component.scss'
})
export class AllArticlesComponent implements OnInit {

  public posts: PostToDisplay[] = [];

  constructor(
    private postService: PostService,
    private toasterService: ToasterService,
  ) {
  }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: res => {
        this.posts = res;
      },
      error: err => {this.toasterService.handleError(err)}
    });

  }
}
