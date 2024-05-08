/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponsePageableResponseListStaffResponse } from '../models/api-response-pageable-response-list-staff-response';
import { ApiResponseStaffResponse } from '../models/api-response-staff-response';
import { ApiResponseVoid } from '../models/api-response-void';
import { fireStaff } from '../fn/staff/fire-staff';
import { FireStaff$Params } from '../fn/staff/fire-staff';
import { getAllStaff } from '../fn/staff/get-all-staff';
import { GetAllStaff$Params } from '../fn/staff/get-all-staff';
import { hireStaff } from '../fn/staff/hire-staff';
import { HireStaff$Params } from '../fn/staff/hire-staff';
import { updateStaff } from '../fn/staff/update-staff';
import { UpdateStaff$Params } from '../fn/staff/update-staff';


/**
 * Endpoints for managing staff details in a vineyard
 */
@Injectable({ providedIn: 'root' })
export class StaffService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllStaff()` */
  static readonly GetAllStaffPath = '/api/v1/vineyards/{vineyardId}/staff';

  /**
   * Get all staff.
   *
   * Get all staff in a vineyard by providing the vineyard id, authenticated user and pageable details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllStaff()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllStaff$Response(params: GetAllStaff$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListStaffResponse>> {
    return getAllStaff(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all staff.
   *
   * Get all staff in a vineyard by providing the vineyard id, authenticated user and pageable details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllStaff$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllStaff(params: GetAllStaff$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListStaffResponse> {
    return this.getAllStaff$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListStaffResponse>): ApiResponsePageableResponseListStaffResponse => r.body)
    );
  }

  /** Path part for operation `updateStaff()` */
  static readonly UpdateStaffPath = '/api/v1/vineyards/{vineyardId}/staff';

  /**
   * Update staff.
   *
   * Update staff details in a vineyard by providing the vineyard id and staff details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateStaff()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateStaff$Response(params: UpdateStaff$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseStaffResponse>> {
    return updateStaff(this.http, this.rootUrl, params, context);
  }

  /**
   * Update staff.
   *
   * Update staff details in a vineyard by providing the vineyard id and staff details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateStaff$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateStaff(params: UpdateStaff$Params, context?: HttpContext): Observable<ApiResponseStaffResponse> {
    return this.updateStaff$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseStaffResponse>): ApiResponseStaffResponse => r.body)
    );
  }

  /** Path part for operation `hireStaff()` */
  static readonly HireStaffPath = '/api/v1/vineyards/{vineyardId}/staff';

  /**
   * Hire a staff.
   *
   * Hire a staff in a vineyard by providing the staff details and vineyard id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `hireStaff()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  hireStaff$Response(params: HireStaff$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return hireStaff(this.http, this.rootUrl, params, context);
  }

  /**
   * Hire a staff.
   *
   * Hire a staff in a vineyard by providing the staff details and vineyard id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `hireStaff$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  hireStaff(params: HireStaff$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.hireStaff$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `fireStaff()` */
  static readonly FireStaffPath = '/api/v1/vineyards/{vineyardId}/staff';

  /**
   * Fire a staff.
   *
   * Fire a staff in a vineyard by providing the staff details and vineyard id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `fireStaff()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  fireStaff$Response(params: FireStaff$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return fireStaff(this.http, this.rootUrl, params, context);
  }

  /**
   * Fire a staff.
   *
   * Fire a staff in a vineyard by providing the staff details and vineyard id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `fireStaff$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  fireStaff(params: FireStaff$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.fireStaff$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

}
