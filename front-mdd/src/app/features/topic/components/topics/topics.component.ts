import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { TopicService } from '../../services/topic.service';
import { Topic } from '../../interfaces/topic.interface';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule, NgFor } from '@angular/common';
import { NavigationEnd, Router } from '@angular/router';


@Component({
    selector: 'app-topics',
    templateUrl: './topics.component.html',
    styleUrl: './topics.component.scss',
    standalone: false
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = []; // Liste des topics
  form: FormGroup; // Formulaire pour créer un topic
  errorMessage: string = '';
  @Input() showSubscribedOnly: boolean = false; // Détermine le type de topics à afficher
  showUnsuscribe: boolean = true;


  constructor(private topicService: TopicService, private fb: FormBuilder, private router:Router) {
 
    // Initialisation du formulaire
    this.form = this.fb.group({
      subject: ['', Validators.required],
      description: ['', Validators.required],
    });
    // Initialisation du booléen en fonction de l'URL
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Vérifie si l'URL contient "/subscription/topics"
        this.showUnsuscribe = event.url === '/subscription/topics' ? false : true;
      }
    });
  }

  ngOnInit(): void {
    this.getTopics();
  }

  // Récupérer les topics
  public getTopics(): void {
    if (this.showSubscribedOnly) {
      this.topicService.getSubscribedTopics().subscribe({
        next: (topics) => (this.topics = topics),
        error: (err) => (this.errorMessage = 'Erreur lors du chargement des topics.'),
      });
    } else {
      this.topicService.getTopics().subscribe({
        next: (topics) => (this.topics = topics),
        error: (err) => (this.errorMessage = 'Erreur lors du chargement des topics.'),
      });
    }
  }

subscribe(idTopic: number): void {

  this.topicService.subscribeUserToTopic(idTopic).subscribe({
    next: () => {
      // Mettez à jour l'état local
      const topic = this.topics.find(t => t.id === idTopic);
      if (topic) {
        topic.userSubscribed = true; // Marque le topic comme abonné
      }
      ;
      this.ngOnInit();
    },
    error: (err) => {
      console.error('Erreur lors de l\'abonnement :', err);
      alert(`Erreur : ${err.status} - ${err.message}`);
    },

  });

}

unsubscribe(idTopic: number): void {

  this.topicService.unsubscribeUserToTopic(idTopic).subscribe({
    next: () => {
      this.topics = this.topics.filter(topic => topic.id !== idTopic);

    },
    error: (err) => {
      console.error('Erreur lors du désabonnement :', err);
      alert(`Erreur : ${err.status} - ${err.message}`);
    },

  });

}
}
