import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {TranslocoPipe} from "@jsverse/transloco";
import {Router} from "@angular/router";
import {AuthService} from "../../../core/services/auth.service";
import {TopicService} from "../../../core/services/topic.service";
import {PostService} from "../../../core/services/post.service";
import {Topic} from "../../../core/models/topic";
import {ToasterService} from "../../../core/services/toaster.service";
import {Post} from "../../../core/models/post";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect, MatSelectTrigger} from "@angular/material/select";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    TranslocoPipe,
    MatOption,
    MatSelect,
    MatSelectTrigger,
    NgForOf,
    NgIf
  ],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss'
})
export class CreateArticleComponent implements OnInit{

  public topics: Topic[]=[];

  topicsTest: Topic[] = [
    {id: 1, name: 'Topic 1', description: "description", createdAt: new Date(), updatedAt: new Date()},
    {id: 2, name: 'Topic 2', description: "description", createdAt: new Date(), updatedAt: new Date()},
    {id: 3, name: 'Topic 3', description: "description", createdAt: new Date(), updatedAt: new Date()},
    {id: 4, name: 'Topic 4', description: "description", createdAt: new Date(), updatedAt: new Date()},
    {id: 5, name: 'Topic 5', description: "description", createdAt: new Date(), updatedAt: new Date()},
    {id: 6, name: 'Topic 6', description: "description", createdAt: new Date(), updatedAt: new Date()},
  ];

  themeControl = new FormControl(this.topicsTest[0], Validators.required);
  titleControl = new FormControl('', [Validators.required]);
  contentControl = new FormControl('', Validators.required);

  submitted: boolean = false;
  creationForm = new FormGroup({
    topic: this.themeControl,
    title: this.titleControl,
    content: this.contentControl,
  });
  hasRegisterError:boolean = false;

  constructor(
    private router: Router,
    private topicService: TopicService,
    private postService: PostService,
    private toasterService: ToasterService,
  ) {}



  ngOnInit(): void {
    this.topicService.getTopics().subscribe({
      next: res => {this.topics = this.topicsTest;
          this.themeControl.setValue(this.topics[0])
        },
      error: err => {this.toasterService.handleError(err)}
    })
  }

  createArticle() {
    if (this.creationForm.valid && this.topic && this.title && this.content) {
      const post: Post = {
        id: null,
        topicId: this.topic.id,
        title: this.title,
        article: this.content,
        authorId: null,
        commentIds: [],
        createdAt: null,
        updatedAt: null
      };
      this.postService.createPost(post).subscribe({
        next: res => {
          this.toasterService.handleSuccess('Article created successfully');
          this.router.navigate(['/articles']);
        },
        error: err => {
          this.toasterService.handleError(err);
          this.hasRegisterError = true;
        }
      });
    } else {
      this.submitted = true;
    }
  }

  hasError(field: string) {
    return this.submitted && !this.creationForm.get(field)?.valid;
  }

  handleChange() {
    this.hasRegisterError = false;
  }

  get topic() {
    return this.creationForm.get('topic')?.value;
  }
  get title() {
    return this.creationForm.get('title')?.value;
  }

  get content() {
    return this.creationForm.get('content')?.value;
  }
}
