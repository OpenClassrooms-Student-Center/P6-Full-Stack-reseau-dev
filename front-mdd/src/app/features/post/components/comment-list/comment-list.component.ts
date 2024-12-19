import { Component, Input, OnInit } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import {  PostComment } from '../../interfaces/comment.interface';

@Component({
  selector: 'app-comment-list',
   templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss'],
  standalone:false
})
export class CommentListComponent implements OnInit{
  postComments:PostComment[]=[];
  errorMessage: string = '';
  @Input() postId!: number; // Post ID reçu du parent
  
  constructor(private commentService:CommentService){}
  ngOnInit(): void {
    console.log("call list comments... for postId:"+this.postId)

    if (this.postId) {
      this.fetchComments(this.postId);
    }
}

    // Récupérer les topics
    fetchComments(postId:number): void {
  
      this.commentService.getComments(postId).subscribe({
        next: (data) => (this.postComments = data),
        error: (err) => (this.errorMessage = 'Erreur lors du chargement des posts.'),
      });
   

}
}
