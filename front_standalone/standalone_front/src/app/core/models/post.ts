export interface Post {
    id: number | null;
    topicId: number;
    article: string;
    title: string;
    authorId: number | null;
    commentIds: number[];
    createdAt: Date | null;
    updatedAt: Date | null;
}

export interface NewPostRequestBody {
  topicId: number;
  content: string;
  title: string;
}


export interface PostToDisplay {
  id: number;
  topicName: string;
  article: string;
  title: string;
  authorName: string;
  createdAt: Date;
  updatedAt: Date;
}
