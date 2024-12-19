import { Component, OnInit } from '@angular/core';
import { Post } from '../../interfaces/post.interface';
import { PostService } from '../../services/post.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-post-detail',
    templateUrl: './post-detail.component.html',
    styleUrl: './post-detail.component.scss',
    standalone: false
})
export class PostDetailComponent implements OnInit {
  public post: Post | undefined;


  constructor(private postService:PostService, private router:Router, private route:ActivatedRoute){ 
  }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!

    this.postService
      .detail(id)
      .subscribe((post: Post) => this.post = post);  }


      
  goBack(): void {
    this.router.navigate(['/']); // Retourne à la page précédente ou d'accueil
  }


}
