import {Component} from '@angular/core';
import {RegisterRequest} from "../../../../core/api/models/register-request";
import {AuthenticationService} from "../../../../core/api/services/authentication.service";
import {Router} from "@angular/router";
import {ApiError} from "../../../../core/api/models/api-error";
import {TokenService} from "../../../../core/api/token/token.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerRequest: RegisterRequest = {email: '', firstName: '', lastName: '', password: ''};
  errorMessages: Array<ApiError> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {
  }

  register() {
    this.errorMessages = [];
    this.authService.register({
      body: this.registerRequest
    })
      .subscribe({
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

  login() {
    this.router.navigate(['auth/login']).then(r => r);
  }

}
