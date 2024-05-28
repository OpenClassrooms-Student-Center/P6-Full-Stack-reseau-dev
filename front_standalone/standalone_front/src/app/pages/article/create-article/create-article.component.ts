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
import {NewPostRequestBody, Post} from "../../../core/models/post";
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

  themeControl = new FormControl(this.topics[0], Validators.required);
  titleControl = new FormControl(null, [Validators.required]);
  contentControl = new FormControl(null, Validators.required);

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
      next: res => {this.topics = res;
        },
      error: err => {this.toasterService.handleError(err)}
    })
  }

  createArticle() {
    if (this.creationForm.valid && this.topic && this.title && this.content) {
      const newPost: NewPostRequestBody = {
        topicId: this.topic.id,
        title: this.title,
        content: this.content,
      };
      this.postService.newPost(newPost).subscribe({
        next: res => {
          this.toasterService.handleSuccess(res.message);
          this.router.navigate(['/home']);
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
