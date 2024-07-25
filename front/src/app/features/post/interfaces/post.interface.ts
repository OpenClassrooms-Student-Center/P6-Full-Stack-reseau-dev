import {Topic} from "../../topic/interfaces/topic.interface";
import {User} from "../../me/interfaces/user.interface";

export interface Post {
  id?: number;
  title: string;
  content: string;
  topic?: Topic;
  comments?: Comment[];
  user?: User;
  created_at?: Date;
  updated_at?: Date;
}
