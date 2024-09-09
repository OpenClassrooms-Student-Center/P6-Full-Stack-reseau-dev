/**
 * Represents a summary of an article.
 */
export type ArticleSummary = {
  id: number;
  articleId: number;
  title: string;
  username: string;
  description: string;
  publicationDate: string;
};

/**
 * Represents a user comment on an article.
 */
export type UserComment = {
  username: string;
  comment: string;
};

/**
 * Represents a full article.
 */
export type Article = {
  id: number;
  title: string;
  description: string;
  authorName: string;
  comments: UserComment[];
  theme: string;
  creationDate: string;
};

/**
 * Represents the values required to create an article.
 */
export type ArticleCreationValues = {
  themeId: string;
  title: string;
  description: string;
};
