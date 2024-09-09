/**
 * Represents the data required to register a user.
 */
export type RegisterRequest = {
  email: string;
  username: string;
  password: string;
};

/**
 * Represents the data required to log in a user.
 */
export type LoginRequest = {
  identifier: string;
  password: string;
};
