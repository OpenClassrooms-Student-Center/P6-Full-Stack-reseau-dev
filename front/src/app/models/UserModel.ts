import {ThemeModelDto} from "./ThemeModelDto";

export interface UserModel {
  id: number;
  username: string;
  email: string;
  password: string;
  themes: ThemeModelDto[];
}
