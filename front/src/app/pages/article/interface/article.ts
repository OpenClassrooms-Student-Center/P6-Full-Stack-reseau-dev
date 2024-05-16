export interface Article {
    id?: number;
    titre: string;
    description: string;
    auteur: string;
    theme_id: number;
    user_id: number;
    createdAt?: Date;
}
