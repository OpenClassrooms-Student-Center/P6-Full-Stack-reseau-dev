import { Component, WritableSignal, input } from '@angular/core';
import { Topic } from '@core/types/topic.type';
import { TopicSubscriptionComponent } from '@components/common/topics/topic-subscription/topic-subscription.component';

/**
 * Represents the container component for managing topics.
 */
@Component({
  selector: 'app-topics-container',
  standalone: true,
  templateUrl: './topics-container.component.html',
  styleUrl: './topics-container.component.scss',
  imports: [TopicSubscriptionComponent],
})
export class TopicsContainerComponent {
  /**
   * The array of topics to be displayed.
   */
  public readonly topicsArray = input.required<WritableSignal<Topic[]>>();

  /**
   * Event handler for subscription changes.
   * @param emittedEvent The emitted event value.
   * @returns {any} Any value returned by the event handler.
   */
  public readonly onSubscriptionChange =
    input.required<(emittedEvent: number) => any>();
}
