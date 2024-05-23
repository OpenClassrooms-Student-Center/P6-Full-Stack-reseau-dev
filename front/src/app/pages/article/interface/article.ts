import { Theme } from "../../theme/interface/theme";

export interface Article {
    articleId: number;
    titre: string;
    description: string;
    auteur: string;
    themeId: number;
    user_id: number;
    createdAt?: Date;
    theme : Theme;
}
