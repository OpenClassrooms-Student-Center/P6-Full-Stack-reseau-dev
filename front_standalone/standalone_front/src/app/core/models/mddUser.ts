export interface MddUser {
    id: number;
    email: string;
    username: string;
    password: string;
    commentIds: number[];
    postIds: number[];
    createdAt: Date;
    updatedAt: Date;
}
