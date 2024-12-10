import {ArticleModel} from "./ArticleModel";

export interface ThemeModel{
  id: number;
  title: string;
  content: string;
  articles: ArticleModel[]
}
