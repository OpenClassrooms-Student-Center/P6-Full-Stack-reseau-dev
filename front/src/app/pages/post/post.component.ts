import { Component, OnInit } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import PostInterface from "../model/post";
import postService from "../services/post.service";
import { DatePipe, NgFor} from "@angular/common";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['post.component.scss'],
  standalone: true,
  imports: [MatCardModule, MatButtonModule, NgFor],
  providers: [DatePipe]
})
export default class Post implements OnInit{
    post_list:PostInterface[] = [];

    private postService;

    constructor(postService: postService){
      this.postService = postService;
    }

    ngOnInit(): void {
      this.getPost_list()
    };
    
    public getPost_list(){
      this.postService.getPostList().subscribe((list)=> list.map((posts)=> 
      this.post_list.push(posts)));

    }

    formatPostDate(date: Date){
     const dateFromPost = new Date(date);
 
     const formattedDate = dateFromPost.toLocaleString(undefined, {day: '2-digit', month:'2-digit', year:'numeric'})

     return formattedDate;
    }
}