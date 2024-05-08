/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponsePageableResponseListUserResponse } from '../models/api-response-pageable-response-list-user-response';
import { ApiResponseString } from '../models/api-response-string';
import { ApiResponseUserResponse } from '../models/api-response-user-response';
import { ApiResponseVoid } from '../models/api-response-void';
import { blockUser } from '../fn/user/block-user';
import { BlockUser$Params } from '../fn/user/block-user';
import { changePassword } from '../fn/user/change-password';
import { ChangePassword$Params } from '../fn/user/change-password';
import { deleteUser } from '../fn/user/delete-user';
import { DeleteUser$Params } from '../fn/user/delete-user';
import { deleteUserById } from '../fn/user/delete-user-by-id';
import { DeleteUserById$Params } from '../fn/user/delete-user-by-id';
import { getMe } from '../fn/user/get-me';
import { GetMe$Params } from '../fn/user/get-me';
import { getUser } from '../fn/user/get-user';
import { GetUser$Params } from '../fn/user/get-user';
import { getUsers } from '../fn/user/get-users';
import { GetUsers$Params } from '../fn/user/get-users';
import { unblockUser } from '../fn/user/unblock-user';
import { UnblockUser$Params } from '../fn/user/unblock-user';
import { updateUser } from '../fn/user/update-user';
import { UpdateUser$Params } from '../fn/user/update-user';


/**
 * Endpoints for user management
 */
@Injectable({ providedIn: 'root' })
export class UserService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getUsers()` */
  static readonly GetUsersPath = '/api/v1/users';

  /**
   * Get all users.
   *
   * Get all users registered in the application
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getUsers()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUsers$Response(params: GetUsers$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListUserResponse>> {
    return getUsers(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all users.
   *
   * Get all users registered in the application
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getUsers$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUsers(params: GetUsers$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListUserResponse> {
    return this.getUsers$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListUserResponse>): ApiResponsePageableResponseListUserResponse => r.body)
    );
  }

  /** Path part for operation `updateUser()` */
  static readonly UpdateUserPath = '/api/v1/users';

  /**
   * Update user.
   *
   * Update the details of the user with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateUser()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateUser$Response(params: UpdateUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseUserResponse>> {
    return updateUser(this.http, this.rootUrl, params, context);
  }

  /**
   * Update user.
   *
   * Update the details of the user with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateUser$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateUser(params: UpdateUser$Params, context?: HttpContext): Observable<ApiResponseUserResponse> {
    return this.updateUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseUserResponse>): ApiResponseUserResponse => r.body)
    );
  }

  /** Path part for operation `deleteUser()` */
  static readonly DeleteUserPath = '/api/v1/users';

  /**
   * Delete user.
   *
   * Delete the currently authenticated user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUser$Response(params?: DeleteUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deleteUser(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete user.
   *
   * Delete the currently authenticated user
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUser(params?: DeleteUser$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deleteUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `changePassword()` */
  static readonly ChangePasswordPath = '/api/v1/users';

  /**
   * Change the password of the authenticated user.
   *
   * Change the password of the authenticated user with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `changePassword()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  changePassword$Response(params: ChangePassword$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseString>> {
    return changePassword(this.http, this.rootUrl, params, context);
  }

  /**
   * Change the password of the authenticated user.
   *
   * Change the password of the authenticated user with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `changePassword$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  changePassword(params: ChangePassword$Params, context?: HttpContext): Observable<ApiResponseString> {
    return this.changePassword$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseString>): ApiResponseString => r.body)
    );
  }

  /** Path part for operation `unblockUser()` */
  static readonly UnblockUserPath = '/api/v1/users/unblock';

  /**
   * Unblock user.
   *
   * Unblock the user with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `unblockUser()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  unblockUser$Response(params: UnblockUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return unblockUser(this.http, this.rootUrl, params, context);
  }

  /**
   * Unblock user.
   *
   * Unblock the user with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `unblockUser$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  unblockUser(params: UnblockUser$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.unblockUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `blockUser()` */
  static readonly BlockUserPath = '/api/v1/users/block';

  /**
   * Block user.
   *
   * Block the user with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `blockUser()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  blockUser$Response(params: BlockUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return blockUser(this.http, this.rootUrl, params, context);
  }

  /**
   * Block user.
   *
   * Block the user with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `blockUser$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  blockUser(params: BlockUser$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.blockUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `getUser()` */
  static readonly GetUserPath = '/api/v1/users/{id}';

  /**
   * Get user with id.
   *
   * Get the details of the user with the given id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUser$Response(params: GetUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseUserResponse>> {
    return getUser(this.http, this.rootUrl, params, context);
  }

  /**
   * Get user with id.
   *
   * Get the details of the user with the given id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUser(params: GetUser$Params, context?: HttpContext): Observable<ApiResponseUserResponse> {
    return this.getUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseUserResponse>): ApiResponseUserResponse => r.body)
    );
  }

  /** Path part for operation `getMe()` */
  static readonly GetMePath = '/api/v1/users/me';

  /**
   * Get the currently authenticated user.
   *
   * Get the details of the currently authenticated user
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getMe()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMe$Response(params?: GetMe$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseUserResponse>> {
    return getMe(this.http, this.rootUrl, params, context);
  }

  /**
   * Get the currently authenticated user.
   *
   * Get the details of the currently authenticated user
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getMe$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMe(params?: GetMe$Params, context?: HttpContext): Observable<ApiResponseUserResponse> {
    return this.getMe$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseUserResponse>): ApiResponseUserResponse => r.body)
    );
  }

  /** Path part for operation `deleteUserById()` */
  static readonly DeleteUserByIdPath = '/api/v1/users/{userId}';

  /**
   * Delete user with id.
   *
   * Delete the user with the given id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteUserById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUserById$Response(params: DeleteUserById$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deleteUserById(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete user with id.
   *
   * Delete the user with the given id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteUserById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUserById(params: DeleteUserById$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deleteUserById$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

}
