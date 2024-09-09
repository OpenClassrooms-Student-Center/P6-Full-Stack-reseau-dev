import { Component, inject, signal } from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';
import { TopicsContainerComponent } from '@components/shared/topics-container/topics-container.component';
import { TopicService } from '@core/services/topic/topic.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { Subscription } from 'rxjs';

/**
 * Represents the component for managing topics.
 */
@Component({
  selector: 'app-topics',
  standalone: true,
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss',
  imports: [TopicSubscriptionComponent, TopicsContainerComponent],
})
export class TopicsComponent {
  /**
   * Service for managing topics
   */
  private readonly topicService = inject(TopicService);

  /**
   * Signal containing an array of topics.
   */
  public topicsArray = signal<Topic[]>([]);

  /**
   * Signal indicating whether topics are currently loading.
   */
  public isLoading = toSignal<boolean>(this.topicService.isLoading$);

  /**
   * Signal indicating whether an error occurred while fetching topics.
   */
  public hasError = toSignal<boolean>(this.topicService.hasError$);

  /**
   * Signal containing the error message, if any, during topic retrieval.
   */
  public errorMessage = toSignal<string>(this.topicService.errorMessage$);

  ngOnInit() {
    const subscription: Subscription = this.topicService
      .getAllThemesWithSubscription()
      .subscribe((res: Topic[]) => {
        this.topicsArray.update(() => {
          return res;
        });
        subscription.unsubscribe();
      });
  }

  /**
   * Updates the user's subscription status for a topic.
   * @param id The ID of the topic.
   */
  public updateUserThemeSubscription = (id: number): void => {
    const topic = this.getTopicById(id) as Topic;
    const { isSubscribed } = topic;

    const subscription: Subscription = this.topicService[
      isSubscribed ? 'unsubscribeToTheme' : 'subscribeToTheme'
    ](id).subscribe(() => {
      this.updateTopicsArray(id);
      subscription.unsubscribe();
    });
  };

  /**
   * Retrieves a topic by its ID.
   * @param id The ID of the topic to retrieve.
   * @returns The topic object if found, otherwise null.
   */
  private getTopicById = (id: number): Topic | null => {
    const topicsArray = this.topicsArray();

    const topic: Topic | null =
      topicsArray.find((topic) => {
        return topic.id === id;
      }) || null;

    return topic;
  };

  /**
   * Updates the topics array with the new subscription status.
   * @param id The ID of the topic to update.
   */
  private updateTopicsArray = (id: number): void => {
    this.topicsArray.update((topics: Topic[]) => {
      const index: number = topics.findIndex((topic: Topic) => topic.id === id);
      if (index !== -1) {
        topics[index].isSubscribed = !topics[index].isSubscribed;
      }

      return topics;
    });
  };
}
