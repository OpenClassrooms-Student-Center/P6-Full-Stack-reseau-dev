import {Component, OnInit} from '@angular/core';
import {TranslocoPipe} from "@jsverse/transloco";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../../core/models/post";
import {PostService} from "../../../core/services/post.service";
import {Comment} from "../../../core/models/comment";
import {MddUserService} from "../../../core/services/mdd-user.service";
import {TopicService} from "../../../core/services/topic.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-article',
  standalone: true,
    imports: [
        TranslocoPipe, CommonModule
    ],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss'
})
export class ArticleComponent implements OnInit{

  post!: Post;

  comments!: Comment[];

  commentsTest: Comment[] = [
    {id: 1, text: "oulalaalalal", postId: 1, authorId: 1, createdAt: new Date(), updatedAt: new Date()},
    {id: 2, text: "oulalaalalal", postId: 1, authorId: 2, createdAt: new Date(), updatedAt: new Date()},
    {id: 3, text: "oulalaalalal", postId: 1, authorId: 3, createdAt: new Date(), updatedAt: new Date()}
  ];

  postsTest: Post[] = [
    {id: 0, topicId: 1, article: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam finibus pretium felis, vitae maximus leo sagittis nec. Fusce tortor elit, imperdiet ut nisl eu, tempor vulputate est. Interdum et malesuada fames ac ante ipsum primis in faucibus. Integer fermentum metus eget rutrum consequat. Donec viverra consectetur metus consectetur bibendum. Duis varius massa sapien, ac molestie est iaculis sed. Suspendisse eu luctus lectus. Nunc tristique ex sed purus mollis, eu convallis dolor sagittis. Duis id ultricies est. Nulla viverra purus sit amet enim elementum, non efficitur turpis lobortis. Phasellus suscipit ultricies mi, id luctus justo ultrices non.\n' +
        '\n' +
        'Morbi ultricies, nulla quis ullamcorper sagittis, dui dui pharetra dui, ac pharetra dui lectus ut orci. Curabitur at massa eu risus vestibulum cursus. Pellentesque posuere, mi vel porta placerat, metus tellus placerat lectus, fermentum semper odio justo et massa. Sed ac ligula a urna lacinia pretium. Maecenas feugiat neque vel lacus hendrerit, a cursus mi tempus. Nulla viverra, ante in posuere aliquet, massa risus pulvinar elit, sed finibus leo felis in risus. Cras a semper ante. Maecenas vehicula sem id felis pulvinar mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed lobortis arcu scelerisque mi ullamcorper blandit. Sed pellentesque, diam in vehicula hendrerit, sem augue efficitur risus, id elementum nibh ex vitae nisi. Praesent ac purus a augue porta lacinia sit amet non urna.', title: 'test1', authorId: 1, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: 1, topicId: 2, article: 'test2', title: 'test2', authorId: 2, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: 2, topicId: 3, article: 'test3', title: 'test3', authorId: 3, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: 3, topicId: 4, article: 'test4', title: 'test4', authorId: 4, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: 4, topicId: 5, article: 'test5', title: 'test5', authorId: 5, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: 5, topicId: 6, article: 'test6', title: 'test6', authorId: 6, commentIds: [], createdAt: new Date(), updatedAt: new Date()},
    {id: 6, topicId: 7, article: 'test7', title: 'test7', authorId: 7, commentIds: [], createdAt: new Date(), updatedAt: new Date()}
  ];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private postService: PostService,
    public mddUserService: MddUserService,
    public topicService: TopicService,
  ) {
  }
  ngOnInit(): void {
    const postId = this.route.snapshot.params['id'];
    this.post = this.postsTest.filter(p => p.id == postId)[0]
    this.comments= this.commentsTest;
  }
  goBack(){
    this.router.navigate(['/home'])
  }


}
