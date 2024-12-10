import {UserModel} from "./UserModel";

export interface CommentModel{
  id:number;
  content:number;
  publishedDate: string;
  author: UserModel;
}
