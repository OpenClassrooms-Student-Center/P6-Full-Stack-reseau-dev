/**
 * Represents a topic.
 */
export type Topic = {
  id: number;
  title: string;
  description: string;
  isSubscribed: boolean;
};

/**
 * Represents options for a topic.
 */
export type TopicOptions = {
  id: number;
  theme: string;
};

/**
 * Represents a subscription to a topic.
 */
export type TopicSubscription = {
  themeId: number;
  isSubscribed: boolean;
};
