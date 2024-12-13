import { Themes } from './themes.interface';
export interface Article {
  id: number; // Identifiant unique de l'article
  title: string; // Titre de l'article
  description: string;
  username: string | null;
  author: string | null;
  themeTitle: string | null;
  messages: {
    // Tableau d'objets représentant les messages associés à l'article
    id: number; // Identifiant unique du message
    userUsername: string; // Nom d'utilisateur de la personne qui a posté le message
    message: string; // Contenu du message
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
  theme: Themes; // Thème de l'article, utilisant l'interface Themes importée
  createdAt: string | null;
  updatedAt: string | null;
}
