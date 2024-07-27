import {Topic} from "../../topic/interfaces/topic.interface";
import {User} from "../../me/interfaces/user.interface";
import {Comment} from "../../comment/interfaces/comment.interface";

export interface Post {
  id?: number;
  title: string;
  content: string;
  topic?: Topic;
  comments?: Comment[];
  user?: User;
  createdAt?: Date;
  updatedAt?: Date;
}
