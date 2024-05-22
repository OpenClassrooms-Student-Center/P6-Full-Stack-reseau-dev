import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
  selector: 'app-comment-form',
  standalone: true,
  imports: [
    MatInput,
    MatLabel,
    MatFormField,
    ReactiveFormsModule,
    TranslocoPipe
  ],
  templateUrl: './comment-form.component.html',
  styleUrl: './comment-form.component.scss'
})
export class CommentFormComponent {

  @Input() commentFormControl!: FormControl;

  //todo: output event
}
