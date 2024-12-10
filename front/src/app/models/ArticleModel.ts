import {AuthorModel} from "./AuthorModel";
import {ThemeModelDto} from "./ThemeModelDto";
import {CommentModel} from "./CommentModel";

export interface ArticleModel {
  id: number;
  title: string;
  content: string;
  publishedDate: Date;
  author: AuthorModel;
  theme: ThemeModelDto;
  comments: CommentModel[];
}
