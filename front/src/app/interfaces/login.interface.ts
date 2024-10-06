export interface LoginForm {
    email: string;              // Email de l'utilisateur
    password: string;           // Mot de passe de l'utilisateur
}

export interface ResponseLogin {
    token: string;              // Jeton d'authentification
    user: {
        id: number;             // Identifiant de l'utilisateur
        username: string;       // Nom d'utilisateur
        email: string;          // Email de l'utilisateur
        role: string;           // Rôle de l'utilisateur
    };
}