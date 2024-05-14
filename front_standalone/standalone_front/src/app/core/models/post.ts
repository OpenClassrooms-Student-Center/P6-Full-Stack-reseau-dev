export interface Post {
    id: number | null;
    topicId: number;
    article: string;
    authorId: number;
    commentIds: number[];
    createdAt: Date;
    updatedAt: Date;
}
