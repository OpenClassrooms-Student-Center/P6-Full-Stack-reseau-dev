import {Component, OnInit} from '@angular/core';
import {PostService} from "../../core/services/post.service";
import {Router} from "@angular/router";
import {Post} from "../../core/models/post";
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
export class HomeComponent implements OnInit{

  public posts: Post[]=[];

  postsTest: Post[] = [
    {id: null, topicId: 1, article: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam finibus pretium felis, vitae maximus leo sagittis nec. Fusce tortor elit, imperdiet ut nisl eu, tempor vulputate est. Interdum et malesuada fames ac ante ipsum primis in faucibus. Integer fermentum metus eget rutrum consequat. Donec viverra consectetur metus consectetur bibendum. Duis varius massa sapien, ac molestie est iaculis sed. Suspendisse eu luctus lectus. Nunc tristique ex sed purus mollis, eu convallis dolor sagittis. Duis id ultricies est. Nulla viverra purus sit amet enim elementum, non efficitur turpis lobortis. Phasellus suscipit ultricies mi, id luctus justo ultrices non.\n' +
        '\n' +
        'Morbi ultricies, nulla quis ullamcorper sagittis, dui dui pharetra dui, ac pharetra dui lectus ut orci. Curabitur at massa eu risus vestibulum cursus. Pellentesque posuere, mi vel porta placerat, metus tellus placerat lectus, fermentum semper odio justo et massa. Sed ac ligula a urna lacinia pretium. Maecenas feugiat neque vel lacus hendrerit, a cursus mi tempus. Nulla viverra, ante in posuere aliquet, massa risus pulvinar elit, sed finibus leo felis in risus. Cras a semper ante. Maecenas vehicula sem id felis pulvinar mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed lobortis arcu scelerisque mi ullamcorper blandit. Sed pellentesque, diam in vehicula hendrerit, sem augue efficitur risus, id elementum nibh ex vitae nisi. Praesent ac purus a augue porta lacinia sit amet non urna.', title: 'test1', authorId: 1, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: null, topicId: 2, article: 'test2', title: 'test2', authorId: 2, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: null, topicId: 3, article: 'test3', title: 'test3', authorId: 3, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: null, topicId: 4, article: 'test4', title: 'test4', authorId: 4, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: null, topicId: 5, article: 'test5', title: 'test5', authorId: 5, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: null, topicId: 6, article: 'test6', title: 'test6', authorId: 6, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: null, topicId: 7, article: 'test7', title: 'test7', authorId: 7, commentIds: [], createdAt: new Date(), updatedAt: new Date()}
  ];

  sortings = ["date", "title", "theme"]

  formSorting = new FormControl('date');

  constructor(
    private postService: PostService,
    private router: Router,
    private toasterService: ToasterService,
  ) {
  }

  ngOnInit(): void {
    this.postService.getPosts().subscribe({
      next: res => {this.posts = this.postsTest},
      error: err => {this.toasterService.handleError(err)}
    })
  }

  selectArticle(articleId: number | null) {
    this.router.navigate(['/article/', articleId]);
  }

  createPost() {
    this.router.navigate(['/article/create'])
  }
}
