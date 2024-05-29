import {Topic} from "./topic";

export interface UserInfo{
  username?: string,
}


export interface UserData {
  username: string,
  email: string,
  topicsIds: Topic[],
}


export interface NewInfo {
  username: string,
  email: string,
}
