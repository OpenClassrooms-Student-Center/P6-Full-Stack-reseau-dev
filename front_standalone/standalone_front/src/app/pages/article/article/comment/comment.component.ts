import {Component, Input, OnInit} from '@angular/core';
import {MatCard, MatCardContent} from "@angular/material/card";
import {MddUserService} from "../../../../core/services/mdd-user.service";
import {Comment, CommentToDisplay} from "../../../../core/models/comment";

@Component({
  selector: 'app-comment',
  standalone: true,
  imports: [
    MatCardContent,
    MatCard
  ],
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.scss'
})
export class CommentComponent{

  @Input() comment!: CommentToDisplay;

  author = ""

  constructor(
    private mddUserService: MddUserService,
  ) {


  }

}
