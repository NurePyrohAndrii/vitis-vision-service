/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addVinesToGroup } from '../fn/group/add-vines-to-group';
import { AddVinesToGroup$Params } from '../fn/group/add-vines-to-group';
import { ApiResponseGroupResponse } from '../models/api-response-group-response';
import { ApiResponsePageableResponseListGroupResponse } from '../models/api-response-pageable-response-list-group-response';
import { ApiResponsePageableResponseListGroupVineResponse } from '../models/api-response-pageable-response-list-group-vine-response';
import { ApiResponseVoid } from '../models/api-response-void';
import { createGroup } from '../fn/group/create-group';
import { CreateGroup$Params } from '../fn/group/create-group';
import { deleteGroup } from '../fn/group/delete-group';
import { DeleteGroup$Params } from '../fn/group/delete-group';
import { getGroup } from '../fn/group/get-group';
import { GetGroup$Params } from '../fn/group/get-group';
import { getGroups } from '../fn/group/get-groups';
import { GetGroups$Params } from '../fn/group/get-groups';
import { getVinesInGroup } from '../fn/group/get-vines-in-group';
import { GetVinesInGroup$Params } from '../fn/group/get-vines-in-group';
import { removeVinesFromGroup } from '../fn/group/remove-vines-from-group';
import { RemoveVinesFromGroup$Params } from '../fn/group/remove-vines-from-group';
import { updateGroup } from '../fn/group/update-group';
import { UpdateGroup$Params } from '../fn/group/update-group';
import { getVinesToAssign } from '../fn/group/get-vines-to-assign';
import {GetVinesToAssign$Params} from "../fn/group/get-vines-to-assign";


/**
 * Endpoints for managing group details in a vineyard
 */
@Injectable({ providedIn: 'root' })
export class GroupService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getGroup()` */
  static readonly GetGroupPath = '/api/v1/vineyards/{vineyardId}/groups/{groupId}';

  /**
   * Get a group in a vineyard.
   *
   * Get a group in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroup$Response(params: GetGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseGroupResponse>> {
    return getGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * Get a group in a vineyard.
   *
   * Get a group in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroup(params: GetGroup$Params, context?: HttpContext): Observable<ApiResponseGroupResponse> {
    return this.getGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseGroupResponse>): ApiResponseGroupResponse => r.body)
    );
  }

  /** Path part for operation `updateGroup()` */
  static readonly UpdateGroupPath = '/api/v1/vineyards/{vineyardId}/groups/{groupId}';

  /**
   * Update a group in a vineyard.
   *
   * Update a group in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateGroup()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateGroup$Response(params: UpdateGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseGroupResponse>> {
    return updateGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * Update a group in a vineyard.
   *
   * Update a group in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateGroup$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateGroup(params: UpdateGroup$Params, context?: HttpContext): Observable<ApiResponseGroupResponse> {
    return this.updateGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseGroupResponse>): ApiResponseGroupResponse => r.body)
    );
  }

  /** Path part for operation `deleteGroup()` */
  static readonly DeleteGroupPath = '/api/v1/vineyards/{vineyardId}/groups/{groupId}';

  /**
   * Delete a group in a vineyard.
   *
   * Delete a group in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteGroup$Response(params: DeleteGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deleteGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete a group in a vineyard.
   *
   * Delete a group in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteGroup(params: DeleteGroup$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deleteGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `getGroups()` */
  static readonly GetGroupsPath = '/api/v1/vineyards/{vineyardId}/groups';

  /**
   * Get all groups in a vineyard.
   *
   * Get all groups in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getGroups()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroups$Response(params: GetGroups$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListGroupResponse>> {
    return getGroups(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all groups in a vineyard.
   *
   * Get all groups in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getGroups$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroups(params: GetGroups$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListGroupResponse> {
    return this.getGroups$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListGroupResponse>): ApiResponsePageableResponseListGroupResponse => r.body)
    );
  }

  /** Path part for operation `createGroup()` */
  static readonly CreateGroupPath = '/api/v1/vineyards/{vineyardId}/groups';

  /**
   * Create a group in a vineyard.
   *
   * Create a group in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createGroup()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createGroup$Response(params: CreateGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseGroupResponse>> {
    return createGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * Create a group in a vineyard.
   *
   * Create a group in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createGroup$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createGroup(params: CreateGroup$Params, context?: HttpContext): Observable<ApiResponseGroupResponse> {
    return this.createGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseGroupResponse>): ApiResponseGroupResponse => r.body)
    );
  }

  /** Path part for operation `getVinesInGroup()` */
  static readonly GetVinesInGroupPath = '/api/v1/vineyards/{vineyardId}/groups/{groupId}/vines';

  /**
   * Get all vines in a group.
   *
   * Get all vines in a group in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVinesInGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVinesInGroup$Response(params: GetVinesInGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListGroupVineResponse>> {
    return getVinesInGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all vines in a group.
   *
   * Get all vines in a group in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVinesInGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVinesInGroup(params: GetVinesInGroup$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListGroupVineResponse> {
    return this.getVinesInGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListGroupVineResponse>): ApiResponsePageableResponseListGroupVineResponse => r.body)
    );
  }

  /** Path part for operation `addVinesToGroup()` */
  static readonly AddVinesToGroupPath = '/api/v1/vineyards/{vineyardId}/groups/{groupId}/vines';

  /**
   * Add vines to a group.
   *
   * Add vines to a group in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addVinesToGroup()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addVinesToGroup$Response(params: AddVinesToGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return addVinesToGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * Add vines to a group.
   *
   * Add vines to a group in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addVinesToGroup$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addVinesToGroup(params: AddVinesToGroup$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.addVinesToGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `removeVinesFromGroup()` */
  static readonly RemoveVinesFromGroupPath = '/api/v1/vineyards/{vineyardId}/groups/{groupId}/vines';

  /**
   * Remove vines from a group.
   *
   * Remove vines from a group in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeVinesFromGroup()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  removeVinesFromGroup$Response(params: RemoveVinesFromGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return removeVinesFromGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * Remove vines from a group.
   *
   * Remove vines from a group in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `removeVinesFromGroup$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  removeVinesFromGroup(params: RemoveVinesFromGroup$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.removeVinesFromGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `getVinesToAssign()` */
  static readonly GetVinesToAssignPath = '/api/v1/vineyards/{vineyardId}/groups/{groupId}/_vines_to_assign';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVinesToAssign()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVinesToAssign$Response(params: GetVinesToAssign$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListGroupVineResponse>> {
    return getVinesToAssign(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVinesToAssign$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVinesToAssign(params: GetVinesToAssign$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListGroupVineResponse> {
    return this.getVinesToAssign$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListGroupVineResponse>): ApiResponsePageableResponseListGroupVineResponse => r.body)
    );
  }

}
