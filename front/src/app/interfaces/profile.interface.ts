export interface Profile {
  id: number; // Identifiant du profil
  username: string; // Nom d'utilisateur
  email: string; // Email de l'utilisateur
  createdAt: Date; // Date de création
  updatedAt: Date; // Date de mise à jour
}

export interface UpdatedUser {
  username: string; // Nouveau nom d'utilisateur
  email: string; // Nouvel email
}
