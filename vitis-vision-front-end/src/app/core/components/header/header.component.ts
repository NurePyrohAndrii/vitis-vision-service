import {Component, OnInit} from '@angular/core';
import {TokenService} from "../../api/token/token.service";
import {Router} from "@angular/router";
import {UserService} from "../../api/services/user.service";
import {UserResponse} from "../../api/models/user-response";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  user: UserResponse = {};

  constructor(
    private tokenService: TokenService,
    private router: Router,
    private userService: UserService,
    public translate: TranslateService
  ) {
    translate.addLangs(['en', 'ua']);
    translate.setDefaultLang('en');

    const browserLang = translate.getBrowserLang();
    translate.use(browserLang?.match(/en|ua/) ? browserLang : 'en');
  }

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

  ngOnInit(): void {
    if (this.isUserAuthenticated()) {
      this.userService.getMe().subscribe({
        next: user => {
          this.user = user.data as UserResponse;
        }
      });
    }
  }

}
