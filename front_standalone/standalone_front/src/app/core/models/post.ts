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
