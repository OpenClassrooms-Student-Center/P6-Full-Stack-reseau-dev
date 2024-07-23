import {Topic} from "../../topic/interfaces/topic.interface";
import {User} from "../../me/interfaces/user.interface";

export interface Post {
  id: number;
  title: string;
  content: string;
  topic: Topic;
  user: User;
  createdAt: Date;
  updatedAt: Date;
}
