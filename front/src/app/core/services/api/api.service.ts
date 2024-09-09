import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { apiParams } from '@core/types/api.type';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment.development';

/**
 * Service for making HTTP requests to the API.
 * @Injectable
 * @providedIn: 'root'
 */
@Injectable({
  providedIn: 'root',
})
export class ApiService {
  /**
   * The base URL for making HTTP requests to the API.
   * It is initialized to the value specified in the environment configuration.
   */
  protected readonly BASE_URL: string = environment.BASE_URL;

  /**
   * Injected HttpClient service for making HTTP requests.
   */
  protected readonly http = inject(HttpClient);

  /**
   * Constructs the complete URL by combining the base URL and the provided URL segment.
   *
   * @param {string} urlPathname - The URL segment to be appended to the base URL.
   * @param {apiParams} params - Optional parameters to be included in the URL.
   * @returns {string} The constructed URL.
   */
  private constructUrl = (urlPathname: string, params?: apiParams): string => {
    const constructedUrl: URL = new URL(this.BASE_URL);
    constructedUrl.pathname = urlPathname;

    if (params) {
      const urlSearchParams = new URLSearchParams();

      for (const param of params) {
        const { parameterName, value } = param;

        urlSearchParams.set(parameterName, value);
      }

      constructedUrl.search = urlSearchParams.toString();
    }

    return constructedUrl.href;
  };

  /**
   * Converts an object of key-value pairs into an array of parameters.
   *
   * @param {Object} object - The object to convert.
   * @returns {apiParams} The array of parameters.
   */
  protected changeObjectParamsToArray = (object: {
    [key: string]: any;
  }): apiParams => {
    const objectEntries = Object.entries(object);

    return objectEntries.map(([prop, value]) => {
      return {
        parameterName: prop,
        value: value,
      };
    });
  };

  /**
   * Performs an HTTP GET request.
   *
   * @param {string} urlPathname - The URL segment to be appended to the base URL.
   * @param {apiParams} params - Optional parameters to be included in the URL.
   * @returns {Observable<T>} An Observable with the response data.
   */
  protected fetchGet = <T>(
    urlPathname: string,
    params?: apiParams
  ): Observable<T> => {
    const constructedUrl: string = this.constructUrl(urlPathname, params);

    return this.http.get<T>(constructedUrl);
  };

  /**
   * Performs an HTTP POST request.
   *
   * @param {string} urlPathname - The URL segment to be appended to the base URL.
   * @param {any} body - The request body.
   * @param {apiParams} params - Optional parameters to be included in the URL.
   * @returns {Observable<T>} An Observable with the response data.
   */
  protected fetchPost = <T>(
    urlPathname: string,
    body: any,
    params?: apiParams
  ): Observable<T> => {
    const constructedUrl: string = this.constructUrl(urlPathname, params);

    return this.http.post<T>(constructedUrl, body);
  };

  /**
   * Performs an HTTP PUT request.
   *
   * @param {string} urlPathname - The URL segment to be appended to the base URL.
   * @param {any} body - The request body.
   * @param {apiParams} params - Optional parameters to be included in the URL.
   * @returns {Observable<T>} An Observable with the response data.
   */
  protected fetchPut = <T>(
    urlPathname: string,
    body: any,
    params?: apiParams
  ): Observable<T> => {
    const constructedUrl: string = this.constructUrl(urlPathname, params);

    return this.http.put<T>(constructedUrl, body);
  };

  /**
   * Performs an HTTP PATCH request.
   *
   * @param {string} urlPathname - The URL segment to be appended to the base URL.
   * @param {any} body - The request body.
   * @param {apiParams} params - Optional parameters to be included in the URL.
   * @returns {Observable<T>} An Observable with the response data.
   */
  protected fetchPatch = <T>(
    urlPathname: string,
    body: any,
    params?: apiParams
  ): Observable<T> => {
    const constructedUrl: string = this.constructUrl(urlPathname, params);

    return this.http.patch<T>(constructedUrl, body);
  };

  /**
   * Performs an HTTP DELETE request.
   *
   * @param {string} urlPathname - The URL segment to be appended to the base URL.
   * @param {apiParams} params - Optional parameters to be included in the URL.
   * @returns {Observable<T>} An Observable with the response data.
   */
  protected fetchDelete = <T>(
    urlPathname: string,
    params?: apiParams
  ): Observable<T> => {
    const constructedUrl: string = this.constructUrl(urlPathname, params);
    return this.http.delete<T>(constructedUrl);
  };
}
