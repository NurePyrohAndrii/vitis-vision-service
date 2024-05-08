import { Component } from '@angular/core';
import {TokenService} from "../../api/token/token.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(
    private tokenService: TokenService,
    private router: Router,

  ) {}

  isUserAuthenticated = (): boolean => {
    return this.tokenService.isTokenValid();
  }

  logOut() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    this.router.navigate(["/auth/login"]).then(r => r);
  }

  checkIsUserAdmin() {
    return this.tokenService.isUserAdmin();
  }

}
