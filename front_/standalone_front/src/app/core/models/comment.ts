export interface Comment {
    id: number;
    text: string;
    postId: number;
    authorId: number;
    createdAt: Date;
    updatedAt: Date;
}


export interface CommentToDisplay{
  id: number;
  text: string;
  authorName: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface NewComment {
  comment: string;
  postId: number;
}
