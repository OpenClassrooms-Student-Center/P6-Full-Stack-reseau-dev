export interface Article {
    id?: number;
    title: string;
    contenu: string;
    auteur: string;
    theme_id: number;
    user_id: number;
    createdAt?: Date;
}
