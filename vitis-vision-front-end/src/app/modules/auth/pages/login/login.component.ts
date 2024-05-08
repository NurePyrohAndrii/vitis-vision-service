import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../../../core/api/models/authentication-request";
import {AuthenticationService} from "../../../../core/api/services/authentication.service";
import {Router} from "@angular/router";
import {ApiError} from "../../../../core/api/models/api-error";
import {TokenService} from "../../../../core/api/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMessages: Array<ApiError> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {
  }

  login() {
    this.errorMessages = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res) => {
        this.tokenService.accessToken = res.data?.access_token as string;
        this.tokenService.refreshToken = res.data?.refresh_token as string;
        this.router.navigate(['/profile']).then(r => r);
      },
      error: (err) => {
        this.errorMessages = err.error.errors;
      }
    });
  }

  register() {
    this.router.navigate(['auth/register']).then(r => r);
  }

}
