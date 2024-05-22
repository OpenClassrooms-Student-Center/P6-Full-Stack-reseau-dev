export interface Comment {
    id: number;
    text: string;
    postId: number;
    authorId: number;
    createdAt: Date;
    updatedAt: Date;
}
