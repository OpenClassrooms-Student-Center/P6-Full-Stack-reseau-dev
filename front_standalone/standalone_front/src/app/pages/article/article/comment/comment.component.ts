import {Component, Input, OnInit} from '@angular/core';
import {MatCard, MatCardContent} from "@angular/material/card";
import {MddUserService} from "../../../../core/services/mdd-user.service";
import {Comment} from "../../../../core/models/comment";

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
export class CommentComponent implements OnInit{

  @Input() comment!: Comment;

  author = ""

  constructor(
    private mddUserService: MddUserService,
  ) {


  }


  ngOnInit(): void {
    this.mddUserService.getUserById(this.comment.authorId).subscribe({next: res => this.author =  res.username})
  }
}
