import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import { MatInput, MatLabel} from "@angular/material/input";
import {TranslocoPipe} from "@jsverse/transloco";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {MatFormFieldModule} from "@angular/material/form-field";

@Component({
  selector: 'app-comment-form',
  standalone: true,
  imports: [
    MatInput,
    MatLabel,
    MatFormFieldModule,
    ReactiveFormsModule,
    TranslocoPipe,
    CdkTextareaAutosize
  ],
  templateUrl: './comment-form.component.html',
  styleUrl: './comment-form.component.scss'
})
export class CommentFormComponent {

  @Input() commentFormControl!: FormControl;
  @Input() errorMessage = "error message"

  constructor() {
  }
}
