export interface LoginForm {
    email: string;
    password: string;
  }
export interface responseLogin {
    token: string;
    user: {
        id: number;
        username: string;
        email: string;
        role: string;
    }
}