import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../core/api/services/user.service";
import {ApiError} from "../../../../core/api/models/api-error";
import {UserResponse} from "../../../../core/api/models/user-response";
import {VineyardResponse} from "../../../../core/api/models/vineyard-response";
import {VineyardService} from "../../../../core/api/services/vineyard.service";
import {ChangePasswordRequest} from "../../../../core/api/models/change-password-request";
import {UserRequest} from "../../../../core/api/models/user-request";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: UserResponse = {};
  vineyard: VineyardResponse = {};
  errorMessages: Array<ApiError> = [];

  updateProfileRequest: UserRequest = {firstName: '', lastName: ''};
  passwordChangeRequest: ChangePasswordRequest = {currentPassword: '', newPassword: '', confirmPassword: ''};

  editProfileMode = false;
  changePasswordMode = false;

  constructor(
    private userService: UserService,
    private vineyardService: VineyardService
  ) {
  }

  ngOnInit(): void {
    this.userService.getMe().subscribe({
      next: (res) => {
        this.user = res.data as UserResponse;
        this.updateProfileRequest.firstName = this.user.firstName || '';
        this.updateProfileRequest.lastName = this.user.lastName || '';
        if (this.user.vineyardId) {
          this.getVineyard(Number(this.user.vineyardId));
        }
      },
    });
  }

  updateProfile(): void {
    this.errorMessages = [];
    this.userService.updateUser({
      body: this.updateProfileRequest
    }).subscribe({
      next: (res) => {
        this.user = res.data as UserResponse;
        this.editProfileMode = false;
      },
      error: (err) => {
        this.errorMessages = err.error.errors;
      }
    });
  }

  toggleEditProfile(): void {
    this.editProfileMode = !this.editProfileMode;
    this.changePasswordMode = false;
  }

  toggleChangePassword(): void {
    this.changePasswordMode = !this.changePasswordMode;
    this.editProfileMode = false;
  }

  private getVineyard(id: number): void {
    this.vineyardService.getVineyard({
      vineyardId: id
    }).subscribe({
      next: (res) => {
        this.vineyard = res.data as VineyardResponse;
      },
    });
  }

}
