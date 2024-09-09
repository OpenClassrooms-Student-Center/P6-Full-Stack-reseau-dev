import { Component, EventEmitter, Output, input } from '@angular/core';

/**
 * Represents the component for topic subscription.
 */
@Component({
  selector: 'app-topic-subscription',
  standalone: true,
  imports: [],
  templateUrl: './topic-subscription.component.html',
  styleUrl: './topic-subscription.component.scss',
})
export class TopicSubscriptionComponent {
  @Output() toggleSubscription = new EventEmitter<number>();

  /**
   * The ID of the topic.
   */
  public readonly id = input.required<number>();

  /**
   * The title of the topic.
   */
  public readonly title = input.required<string>();

  /**
   * The description of the topic.
   */
  public readonly description = input.required<string>();

  /**
   * Indicates whether the user is subscribed to the topic or not.
   */
  public readonly isSubscribed = input.required<boolean>();

  /**
   * Toggles the subscription status of the topic.
   * Emits the ID of the topic when toggling subscription.
   */
  toggleTopicSubscription = (): void => {
    const id = this.id();

    this.toggleSubscription.emit(id);
  };
}
