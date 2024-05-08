/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponsePageableResponseListVineyardResponse } from '../models/api-response-pageable-response-list-vineyard-response';
import { ApiResponseVineyardResponse } from '../models/api-response-vineyard-response';
import { ApiResponseVoid } from '../models/api-response-void';
import { createVineyard } from '../fn/vineyard/create-vineyard';
import { CreateVineyard$Params } from '../fn/vineyard/create-vineyard';
import { deleteVineyard } from '../fn/vineyard/delete-vineyard';
import { DeleteVineyard$Params } from '../fn/vineyard/delete-vineyard';
import { getVineyard } from '../fn/vineyard/get-vineyard';
import { GetVineyard$Params } from '../fn/vineyard/get-vineyard';
import { getVineyards } from '../fn/vineyard/get-vineyards';
import { GetVineyards$Params } from '../fn/vineyard/get-vineyards';
import { updateVineyard } from '../fn/vineyard/update-vineyard';
import { UpdateVineyard$Params } from '../fn/vineyard/update-vineyard';


/**
 * Endpoints for managing vineyards details
 */
@Injectable({ providedIn: 'root' })
export class VineyardService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getVineyard()` */
  static readonly GetVineyardPath = '/api/v1/vineyards/{vineyardId}';

  /**
   * Get a vineyard.
   *
   * Get a vineyard with the provided id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVineyard()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVineyard$Response(params: GetVineyard$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineyardResponse>> {
    return getVineyard(this.http, this.rootUrl, params, context);
  }

  /**
   * Get a vineyard.
   *
   * Get a vineyard with the provided id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVineyard$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVineyard(params: GetVineyard$Params, context?: HttpContext): Observable<ApiResponseVineyardResponse> {
    return this.getVineyard$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVineyardResponse>): ApiResponseVineyardResponse => r.body)
    );
  }

  /** Path part for operation `updateVineyard()` */
  static readonly UpdateVineyardPath = '/api/v1/vineyards/{vineyardId}';

  /**
   * Update a vineyard.
   *
   * Update a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateVineyard()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVineyard$Response(params: UpdateVineyard$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineyardResponse>> {
    return updateVineyard(this.http, this.rootUrl, params, context);
  }

  /**
   * Update a vineyard.
   *
   * Update a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateVineyard$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVineyard(params: UpdateVineyard$Params, context?: HttpContext): Observable<ApiResponseVineyardResponse> {
    return this.updateVineyard$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVineyardResponse>): ApiResponseVineyardResponse => r.body)
    );
  }

  /** Path part for operation `deleteVineyard()` */
  static readonly DeleteVineyardPath = '/api/v1/vineyards/{vineyardId}';

  /**
   * Delete a vineyard.
   *
   * Delete a vineyard with the provided id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteVineyard()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteVineyard$Response(params: DeleteVineyard$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deleteVineyard(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete a vineyard.
   *
   * Delete a vineyard with the provided id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteVineyard$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteVineyard(params: DeleteVineyard$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deleteVineyard$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `getVineyards()` */
  static readonly GetVineyardsPath = '/api/v1/vineyards';

  /**
   * Get vineyards.
   *
   * Get a list of vineyards
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVineyards()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVineyards$Response(params: GetVineyards$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListVineyardResponse>> {
    return getVineyards(this.http, this.rootUrl, params, context);
  }

  /**
   * Get vineyards.
   *
   * Get a list of vineyards
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVineyards$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVineyards(params: GetVineyards$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListVineyardResponse> {
    return this.getVineyards$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListVineyardResponse>): ApiResponsePageableResponseListVineyardResponse => r.body)
    );
  }

  /** Path part for operation `createVineyard()` */
  static readonly CreateVineyardPath = '/api/v1/vineyards';

  /**
   * Create a new vineyard.
   *
   * Create a new vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createVineyard()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVineyard$Response(params: CreateVineyard$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineyardResponse>> {
    return createVineyard(this.http, this.rootUrl, params, context);
  }

  /**
   * Create a new vineyard.
   *
   * Create a new vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createVineyard$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVineyard(params: CreateVineyard$Params, context?: HttpContext): Observable<ApiResponseVineyardResponse> {
    return this.createVineyard$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVineyardResponse>): ApiResponseVineyardResponse => r.body)
    );
  }

}
