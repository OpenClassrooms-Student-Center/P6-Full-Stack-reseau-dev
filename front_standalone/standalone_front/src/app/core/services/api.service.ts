import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {JWTService} from "./jwt.service";

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  pipe(arg0: any) {
    throw new Error('Method not implemented.');
  }
  constructor(private http: HttpClient,
              private jwtSerice: JWTService) {}

  private formatErrors(error: any) {

    return throwError(error.error);
  }

  get(path: string): Observable<any> {
    console.log('GET : ', `/api${path}`)
    return this.http
      .get(`/api${path}`,
{
      })
      .pipe(catchError(this.formatErrors));
  }

  getNoAuth(path: string): Observable<any> {
    return this.http
      .get(`/api${path}`,
        {
        })
      .pipe(catchError(this.formatErrors));
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http
      .put(
        `/api${path}`,
        JSON.stringify(body),
        {
          headers: new HttpHeaders(
            {
              'content-type': 'application/json',
            })
        })
      .pipe(catchError(this.formatErrors));
  }

  post(path: string, body: Object = {}): Observable<any> {
    return this.http
      .post(
        `/api${path}`,
        JSON.stringify(body),
        {
          headers: new HttpHeaders(
            {
              'content-type': 'application/json',
            })
        })
      .pipe(catchError(this.formatErrors));
  }

  delete(path: string): Observable<any> {
    return this.http
      .delete(`/api${path}`)
      .pipe(catchError(this.formatErrors));
  }

}
