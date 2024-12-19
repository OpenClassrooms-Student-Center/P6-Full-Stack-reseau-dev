import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { Post } from '../../interfaces/post.interface';
import { Topic } from 'src/app/features/topic/interfaces/topic.interface';
import { TopicService } from 'src/app/features/topic/services/topic.service';
import { TopicsComponent } from 'src/app/features/topic/components/topics/topics.component';

@Component({
    selector: 'app-post-create',
    templateUrl: './post-create.component.html',
    styleUrl: './post-create.component.scss',
    standalone: false
})
export class PostCreateComponent {
    errorMessage: string = '';

      topics: Topic[] = []; // Liste des topics
    

    form!: FormGroup;
      constructor(private fb: FormBuilder, private router: Router,private postService:PostService,
            private topicService:TopicService
        
      ) {}
    
      ngOnInit(): void {
        this.form = this.fb.group({
          topicSubject: ['', [Validators.required]],
          title: ['', [Validators.required, Validators.minLength(6)]],
          content: ['', [Validators.required, Validators.minLength(6)]],

        });
        this.loadSubscribedTopics();
        }
    
      onSubmit(): void {
        if (this.form.valid) {
          console.log('post Submitted', this.form.value);
          const post = this.form.value as Post;
         this.postService.createPost(post).subscribe({
            next: () =>            
              this.router.navigate(['post/list'])
            ,  error: (err) => {
              this.errorMessage = 'Erreur lors de la création du post.';
              console.error(err);
            },
          });
        } else {
          this.errorMessage = 'Veuillez remplir tous les champs correctement.';
        }
        
      }
    
      goBack(): void {
        this.router.navigate(['/']); // Retourne à la page précédente ou d'accueil
      }


        // Récupérer les topics
  public loadSubscribedTopics(): void {
       this.topicService.getSubscribedTopics().subscribe({
        next: (topics) => (this.topics = topics),
        error: (err) => (this.errorMessage = 'Erreur lors du chargement des topics.'),
      });
   
  }
}
