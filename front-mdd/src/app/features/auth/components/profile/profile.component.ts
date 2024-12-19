import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from '../../../../shared/services/session.service';
import { TopicService } from '../../../topic/services/topic.service';
import { Topic } from '../../../topic/interfaces/topic.interface';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss'],
    standalone: false
})
export class ProfileComponent implements OnInit {
  public profileForm: FormGroup;
  public subscriptions = [
    { id: 1, title: 'Titre du thème 1', description: 'Description du thème 1' },
    { id: 2, title: 'Titre du thème 2', description: 'Description du thème 2' },
  ];
  topics: Topic[] = []; // Liste des topics
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private router: Router,
    private topicService:TopicService
  ) {


    this.profileForm = this.fb.group({
      username: [this.sessionService.sessionInformation?.username || ''],
      email: [this.sessionService.sessionInformation?.email || ''],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    console.log('Profil mis à jour :', this.profileForm.value);
    // Logique pour enregistrer les modifications
  }

  logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  unsubscribe(subscriptionId: number): void {
    console.log('Se désabonner de l’abonnement :', subscriptionId);
    // Logique pour se désabonner
  }

    // Récupérer les topics
    getUbscribedTopics(): void {
      console.log('list topics call.....')
      this.topicService.getSubscribedTopics().subscribe({
        next: (topics) => {
          this.topics = topics;
          console.log('topics:',JSON.stringify(topics, null, 2));
        },
        error: (err) => (this.errorMessage = 'Erreur lors du chargement des topics.'),
      });
    }
}
