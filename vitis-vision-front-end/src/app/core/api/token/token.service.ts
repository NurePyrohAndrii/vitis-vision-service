import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set accessToken(accessToken: string) {
    localStorage.setItem('access_token', accessToken);
  }

  get accessToken(): string {
    return localStorage.getItem('access_token') as string;
  }

  set refreshToken(refreshToken: string) {
    localStorage.setItem('refresh_token', refreshToken);
  }

  get refreshToken(): string {
    return localStorage.getItem('refresh_token') as string;
  }

  isTokenValid() {
    const token = this.accessToken;

    if (!token) {
      return false;
    }

    const jwtHelper = new JwtHelperService();
    const isTokenExpired = jwtHelper.isTokenExpired(token);
    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }
    return true;
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  isUserAdmin() {
    const token = localStorage.getItem("access_token");
    if (token) {
      const jwtHelper = new JwtHelperService();
      const decodedToken = jwtHelper.decodeToken(token);
      if (decodedToken.role == "ADMIN") {
        return true;
      }
    }
    return false;
  }

}
