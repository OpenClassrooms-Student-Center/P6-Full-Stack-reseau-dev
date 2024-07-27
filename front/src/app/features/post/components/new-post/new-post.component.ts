import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Topic} from "../../../topic/interfaces/topic.interface";
import {TopicService} from "../../../topic/services/topic.service";
import {Post} from "../../interfaces/post.interface";
import {Location} from "@angular/common";
import {Router} from "@angular/router";
import {PostService} from "../../services/post.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.scss']
})
export class NewPostComponent implements OnInit {

  public onError = false;
  public errorMessage = "";

  public topics: Topic[] = [];

  private postSubscription!: Subscription;
  private topicSubscription!: Subscription;

  public form = this.fb.group({
    topic: [
      '',
      [
      ]
    ],
    title: [
      '',
      [
        Validators.required,
      ]
    ],
    content: [
      '',
      [
        Validators.required,
      ]
    ]
  });

  constructor(
    private topicService:TopicService,
    private postService:PostService,
    private fb: FormBuilder,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.topicSubscription = this.topicService.findAll().subscribe((topics: Topic[]) => {
      this.topics = topics;
    });
  }

  submit() :void
  {
    const id = Number(this.form.value.topic);
    if(id){
      const post: Post = {
        title: this.form.value.title??'',
        content: this.form.value.content??'',
        topic: this.topics.find((topic: Topic) => topic.id === id),
        user: JSON.parse(sessionStorage.getItem('user') as string)
      };
      this.postSubscription = this.postService.create(post).subscribe({
          next: (post: Post) => this.router.navigate([`/post/${post.id}`]),
          error: (error) => {
            this.onError = true;
            this.errorMessage = error.error.message;
          }
        }
      );

    }
  }

  goBack(): void {
    this.location.back();
  }

  ngOnDestroy(): void {
    if (this.postSubscription) {
      this.postSubscription.unsubscribe();
    }
    if (this.topicSubscription) {
      this.topicSubscription.unsubscribe();
    }
  }

}
