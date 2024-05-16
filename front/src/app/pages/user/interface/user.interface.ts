import { Theme } from "../../theme/interface/theme";

export interface User {
    id: number;
    email: string;
    firstName: string;
    password: string;
    createdAt: Date;
    themes : Theme[];
    articles : [];
}
