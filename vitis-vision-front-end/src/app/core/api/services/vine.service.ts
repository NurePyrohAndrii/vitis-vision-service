/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponsePageableResponseListVineResponse } from '../models/api-response-pageable-response-list-vine-response';
import { ApiResponseVineResponse } from '../models/api-response-vine-response';
import { ApiResponseVoid } from '../models/api-response-void';
import { createVine } from '../fn/vine/create-vine';
import { CreateVine$Params } from '../fn/vine/create-vine';
import { deleteVine } from '../fn/vine/delete-vine';
import { DeleteVine$Params } from '../fn/vine/delete-vine';
import { getVine } from '../fn/vine/get-vine';
import { GetVine$Params } from '../fn/vine/get-vine';
import { getVines } from '../fn/vine/get-vines';
import { GetVines$Params } from '../fn/vine/get-vines';
import { updateVine } from '../fn/vine/update-vine';
import { UpdateVine$Params } from '../fn/vine/update-vine';


/**
 * Endpoints for managing vine details in a block
 */
@Injectable({ providedIn: 'root' })
export class VineService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getVine()` */
  static readonly GetVinePath = '/api/v1/blocks/{blockId}/vines/{vineId}';

  /**
   * Get the vine details in a block.
   *
   * Get the vine details in a block with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVine()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVine$Response(params: GetVine$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineResponse>> {
    return getVine(this.http, this.rootUrl, params, context);
  }

  /**
   * Get the vine details in a block.
   *
   * Get the vine details in a block with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVine$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVine(params: GetVine$Params, context?: HttpContext): Observable<ApiResponseVineResponse> {
    return this.getVine$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVineResponse>): ApiResponseVineResponse => r.body)
    );
  }

  /** Path part for operation `updateVine()` */
  static readonly UpdateVinePath = '/api/v1/blocks/{blockId}/vines/{vineId}';

  /**
   * Update the vine details in a block.
   *
   * Update the vine details in a block with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateVine()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVine$Response(params: UpdateVine$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineResponse>> {
    return updateVine(this.http, this.rootUrl, params, context);
  }

  /**
   * Update the vine details in a block.
   *
   * Update the vine details in a block with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateVine$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVine(params: UpdateVine$Params, context?: HttpContext): Observable<ApiResponseVineResponse> {
    return this.updateVine$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVineResponse>): ApiResponseVineResponse => r.body)
    );
  }

  /** Path part for operation `deleteVine()` */
  static readonly DeleteVinePath = '/api/v1/blocks/{blockId}/vines/{vineId}';

  /**
   * Delete the vine details in a block.
   *
   * Delete the vine details in a block with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteVine()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteVine$Response(params: DeleteVine$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deleteVine(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete the vine details in a block.
   *
   * Delete the vine details in a block with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteVine$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteVine(params: DeleteVine$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deleteVine$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `getVines()` */
  static readonly GetVinesPath = '/api/v1/blocks/{blockId}/vines';

  /**
   * Get all vines in a block.
   *
   * Get all vines in a block with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVines()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVines$Response(params: GetVines$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListVineResponse>> {
    return getVines(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all vines in a block.
   *
   * Get all vines in a block with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVines$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVines(params: GetVines$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListVineResponse> {
    return this.getVines$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListVineResponse>): ApiResponsePageableResponseListVineResponse => r.body)
    );
  }

  /** Path part for operation `createVine()` */
  static readonly CreateVinePath = '/api/v1/blocks/{blockId}/vines';

  /**
   * Create a new vine in a block.
   *
   * Create a new vine in a block with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createVine()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVine$Response(params: CreateVine$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineResponse>> {
    return createVine(this.http, this.rootUrl, params, context);
  }

  /**
   * Create a new vine in a block.
   *
   * Create a new vine in a block with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createVine$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVine(params: CreateVine$Params, context?: HttpContext): Observable<ApiResponseVineResponse> {
    return this.createVine$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVineResponse>): ApiResponseVineResponse => r.body)
    );
  }

}
