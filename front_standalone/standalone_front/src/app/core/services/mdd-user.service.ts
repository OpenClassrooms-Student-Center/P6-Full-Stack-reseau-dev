import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { MddUser } from '../models/mddUser';
import {NewInfo, UserData} from "../models/userInfo";
import {MessageResponse} from "../models/messages";

@Injectable({
  providedIn: 'root'
})
export class MddUserService {

    constructor(private apiService: ApiService) { }

    getUsers(): Observable<MddUser[]> {
        return this.apiService.get('/users/user/users');
    }

    getUserById(id: number): Observable<MddUser> {
        return this.apiService.get(`/users/user/${id}`);
    }

    createUser(user: MddUser): Observable<MddUser> {
        return this.apiService.post('/users/user/create', user);
    }

    updateUser(user: MddUser): Observable<MddUser> {
        return this.apiService.put('/users/user/update', user);
    }

    deleteUser(id: number): Observable<void> {
        return this.apiService.delete(`/users/user/${id}`);
    }

    getMe(): Observable<UserData> {
      return this.apiService.get(`/users/user/contactinfo`)
    }

  newInfo(newInfo: NewInfo): Observable<MessageResponse> {
    return this.apiService.put('/users/user/newinfo', newInfo);
  }
}
