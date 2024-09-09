import { Injectable } from '@angular/core';
import { CookieService, CookieType } from '@lephenix47/cookies-utility';

/**
 * Service responsible for managing cookies, specifically the JWT in the cookies.
 * @Injectable
 * @providedIn: 'root'
 */
@Injectable({
  providedIn: 'root',
})
export class CookiesService {
  /**
   * The name of the JWT in the cookies.
   */
  private TOKEN_KEY_NAME: string = 'jwt';

  /**
   * Retrieves the JWT in the cookies.
   * @returns The JWT in the cookies or null if not found.
   */
  public getJwt(): CookieType | null {
    return CookieService.getCookieByName(this.TOKEN_KEY_NAME);
  }

  /**
   * Sets the JWT in the cookies.
   * @param jwt - The JWT to be stored in the cookie.
   */
  public setJwt(jwt: string): void {
    CookieService.setCookie(this.TOKEN_KEY_NAME, jwt);
  }

  /**
   * Deletes the JWT in the cookies.
   */
  public deleteJwt(): void {
    CookieService.deleteCookieByName(this.TOKEN_KEY_NAME);
  }
}
