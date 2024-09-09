/**
 * Represents a user entity.
 */
export type UserEntity = {
  id: number;
  username: string;
  email: string;
  createdAt: string;
  updatedAt: string;
};

/**
 * Represents user information.
 */
export type UserInfo = {
  token: string | null;
  id: number | null;
  username: string | null;
  email: string | null;
};

/**
 * Represents basic user information.
 */
export type UserBasicInfo = Omit<UserInfo, 'token'>;
