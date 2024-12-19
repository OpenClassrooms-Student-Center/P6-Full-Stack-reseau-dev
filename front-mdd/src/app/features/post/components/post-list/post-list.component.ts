import { Component, HostListener, OnInit } from '@angular/core';
import { Post } from '../../interfaces/post.interface';
import { PostService } from '../../services/post.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-post-list',
    templateUrl: './post-list.component.html',
    styleUrls: ['./post-list.component.scss'],
    standalone: false
})
export class PostListComponent implements OnInit{
  posts:Post[] =[];
  errorMessage: string = '';
  gridCols: number = 2; // Nombre de colonnes par défaut
  sortOrder!:string;


  constructor(private postService: PostService, private router:Router) {}

  ngOnInit(): void {
this.getPosts();
this.adjustGridCols(window.innerWidth);
this.sortOrder='desc';

}

  // Récupérer les topics
  getPosts(): void {
  
      this.postService.getPosts().subscribe({
        next: (posts) => (this.posts = posts),
        error: (err) => (this.errorMessage = 'Erreur lors du chargement des posts.'),
      });
   
  }

 

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    const target = event.target as Window;
    this.adjustGridCols(target.innerWidth);
  }

  private adjustGridCols(width: number): void {
    this.gridCols = width < 768 ? 1 : 2; // 1 colonne pour mobile, 2 pour écran large
  }

  sortByCreationDateDescOrAsc(order: 'asc' | 'desc'): void {
    this.posts.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return order === 'asc' ? dateA - dateB : dateB - dateA;
    });
  }
  
  toggleSortOrder(): void {
    // Alternance entre 'asc' et 'desc'
    const currentOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.sortOrder = currentOrder;
    this.sortByCreationDateDescOrAsc(currentOrder);
  }


}
