import { Themes } from "./themes.interface";
export interface Article {
    id: number;
    title: string;
    description: string;
    username: string | null;  // Peut être null si pas d'utilisateur associé
    author: string | null;     // Assure-toi d'ajouter 'author'
    themeTitle: string | null;      // Assure-toi d'ajouter 'theme'
    messages: {
        id: number;
        userUsername: string;
        message: string;
    }[];
    createdAt: string | null;
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
    theme: Themes;
    createdAt: string | null; 
    updatedAt: string | null; 
}