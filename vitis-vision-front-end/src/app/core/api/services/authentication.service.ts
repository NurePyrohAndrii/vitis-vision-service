/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponseAuthResponse } from '../models/api-response-auth-response';
import { authenticate } from '../fn/authentication/authenticate';
import { Authenticate$Params } from '../fn/authentication/authenticate';
import { refresh } from '../fn/authentication/refresh';
import { Refresh$Params } from '../fn/authentication/refresh';
import { register } from '../fn/authentication/register';
import { Register$Params } from '../fn/authentication/register';


/**
 * Endpoints for user authentication and registration
 */
@Injectable({ providedIn: 'root' })
export class AuthenticationService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `register()` */
  static readonly RegisterPath = '/api/v1/auth/register';

  /**
   * Register a new user.
   *
   * Register a new user with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `register()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  register$Response(params: Register$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseAuthResponse>> {
    return register(this.http, this.rootUrl, params, context);
  }

  /**
   * Register a new user.
   *
   * Register a new user with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `register$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  register(params: Register$Params, context?: HttpContext): Observable<ApiResponseAuthResponse> {
    return this.register$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseAuthResponse>): ApiResponseAuthResponse => r.body)
    );
  }

  /** Path part for operation `refresh()` */
  static readonly RefreshPath = '/api/v1/auth/refresh';

  /**
   * Refresh an access token.
   *
   * Refresh an access token using the refresh token
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `refresh()` instead.
   *
   * This method doesn't expect any request body.
   */
  refresh$Response(params: Refresh$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseAuthResponse>> {
    return refresh(this.http, this.rootUrl, params, context);
  }

  /**
   * Refresh an access token.
   *
   * Refresh an access token using the refresh token
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `refresh$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  refresh(params: Refresh$Params, context?: HttpContext): Observable<ApiResponseAuthResponse> {
    return this.refresh$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseAuthResponse>): ApiResponseAuthResponse => r.body)
    );
  }

  /** Path part for operation `authenticate()` */
  static readonly AuthenticatePath = '/api/v1/auth/authenticate';

  /**
   * Authenticate a user.
   *
   * Authenticate a user with the provided credentials
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `authenticate()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  authenticate$Response(params: Authenticate$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseAuthResponse>> {
    return authenticate(this.http, this.rootUrl, params, context);
  }

  /**
   * Authenticate a user.
   *
   * Authenticate a user with the provided credentials
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `authenticate$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  authenticate(params: Authenticate$Params, context?: HttpContext): Observable<ApiResponseAuthResponse> {
    return this.authenticate$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseAuthResponse>): ApiResponseAuthResponse => r.body)
    );
  }

}
