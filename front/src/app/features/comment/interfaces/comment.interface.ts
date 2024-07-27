import {User} from "../../me/interfaces/user.interface";
import {Post} from "../../post/interfaces/post.interface";

export interface Comment {
    id?: number;
    user?: User;
    postId?: number;
    content?: string;
    createdAt?: Date;
    updatedAt?: Date;
}
