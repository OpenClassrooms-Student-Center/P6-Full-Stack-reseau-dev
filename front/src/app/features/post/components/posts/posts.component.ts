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
      this.postsSubject.next(posts);
    });
  }

  sort(){
    this.sortAsc = !this.sortAsc;
    const icon = document.getElementById('sort-icon');
    if(icon){
      icon.innerText === 'arrow_downward' ? icon.innerText = 'arrow_upward' : icon.innerText = 'arrow_downward';
    }

    const posts = this.postsSubject.getValue().sort((a, b) => {
      // Convert dates to timestamps and handle potentially undefined values
      const dateA = a.createdAt ? new Date(a.createdAt).getTime() : 0;
      const dateB = b.createdAt ? new Date(b.createdAt).getTime() : 0;
      console.log(dateA,a);
      console.log(dateB,b);
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
    this.postsSubject.next(posts);
  }

}
