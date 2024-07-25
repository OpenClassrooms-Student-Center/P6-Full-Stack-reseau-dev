import { Component, OnInit } from '@angular/core';
import {Post} from "../../interfaces/post.interface";
import {PostService} from "../../services/post.service";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  private postsSubject = new BehaviorSubject<Post[]>([]);
  public posts$ = this.postsSubject.asObservable();

  public sortAsc = true;

  constructor(private postService:PostService) { }

  ngOnInit(): void {
    this.postService.findAll().subscribe((posts: Post[]) => {
      console.log(posts);
      this.postsSubject.next(posts);
    });
  }

  sort(){
    this.sortAsc = !this.sortAsc;
    const icon = document.getElementById('sort-icon');
    if(icon){
      console.log( icon.innerText)
      icon.innerText === 'arrow_downward' ? icon.innerText = 'arrow_upward' : icon.innerText = 'arrow_downward';
      console.log( icon.innerText)
    }

    const posts = this.postsSubject.getValue().sort((a, b) => {
      // Convert dates to timestamps and handle potentially undefined values
      const dateA = a.created_at ? new Date(a.created_at).getTime() : 0;
      const dateB = b.created_at ? new Date(b.created_at).getTime() : 0;
      console.log(dateA,a);
      console.log(dateB,b);
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
    console.log(posts);
    this.postsSubject.next(posts);
  }

}
