import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Theme } from '../interface/theme';
import { Observable } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private pathService = 'api/theme';

  constructor(private httpClient: HttpClient, ) { 
  }

  public all(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.pathService);
  }

  public getById(id: string): Observable<Theme> {
    return this.httpClient.get<Theme>(`${this.pathService}/${id}`);
  }
}
