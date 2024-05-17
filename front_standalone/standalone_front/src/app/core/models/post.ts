export interface Post {
    id: number | null;
    topicId: number;
    article: string;
    title: string;
    authorId: number;
    commentIds: number[];
    createdAt: Date;
    updatedAt: Date;
}
