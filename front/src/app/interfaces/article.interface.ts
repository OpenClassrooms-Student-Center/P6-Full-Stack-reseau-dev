import { Themes } from "./themes.interface";
export interface Article {
    id: number;
    title: string;
    description: string;
    username: string;
    messages: {
        id: number;
        userUsername: string;
        message: string;
    }[];
    themes: Themes;
    created_at: String | null;
    updatedAt: Date | null;
}
export interface ArticlePage {
    articleId: number;
    title: string;
    description: string;
    username: string;
    messages: {
        id: number;
        userUsername: string;
        message: string;
    }[];
    themes: Themes;
    created_at: String | null;
    updatedAt: Date | null;
}