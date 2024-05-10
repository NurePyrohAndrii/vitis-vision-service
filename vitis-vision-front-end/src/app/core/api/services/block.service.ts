/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponseBlockResponse } from '../models/api-response-block-response';
import { ApiResponsePageableResponseListBlockResponse } from '../models/api-response-pageable-response-list-block-response';
import { ApiResponseVoid } from '../models/api-response-void';
import {CreateBlock$Params, createBlock} from '../fn/block/create-vineyard-1';
import { deleteBlock } from '../fn/block/delete-block';
import { DeleteBlock$Params } from '../fn/block/delete-block';
import { generateBlockReport } from '../fn/block/generate-block-report';
import { GenerateBlockReport$Params } from '../fn/block/generate-block-report';
import { getBlock } from '../fn/block/get-block';
import { GetBlock$Params } from '../fn/block/get-block';
import { getBlocks } from '../fn/block/get-blocks';
import { GetBlocks$Params } from '../fn/block/get-blocks';
import { updateBlock } from '../fn/block/update-block';
import { UpdateBlock$Params } from '../fn/block/update-block';


/**
 * Endpoints for managing block details in a vineyard
 */
@Injectable({ providedIn: 'root' })
export class BlockService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getBlock()` */
  static readonly GetBlockPath = '/api/v1/vineyards/{vineyardId}/blocks/{blockId}';

  /**
   * Get a block in a vineyard.
   *
   * Get a block in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBlock()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBlock$Response(params: GetBlock$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseBlockResponse>> {
    return getBlock(this.http, this.rootUrl, params, context);
  }

  /**
   * Get a block in a vineyard.
   *
   * Get a block in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBlock$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBlock(params: GetBlock$Params, context?: HttpContext): Observable<ApiResponseBlockResponse> {
    return this.getBlock$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseBlockResponse>): ApiResponseBlockResponse => r.body)
    );
  }

  /** Path part for operation `updateBlock()` */
  static readonly UpdateBlockPath = '/api/v1/vineyards/{vineyardId}/blocks/{blockId}';

  /**
   * Update a block in a vineyard.
   *
   * Update a block in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateBlock()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateBlock$Response(params: UpdateBlock$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseBlockResponse>> {
    return updateBlock(this.http, this.rootUrl, params, context);
  }

  /**
   * Update a block in a vineyard.
   *
   * Update a block in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateBlock$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateBlock(params: UpdateBlock$Params, context?: HttpContext): Observable<ApiResponseBlockResponse> {
    return this.updateBlock$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseBlockResponse>): ApiResponseBlockResponse => r.body)
    );
  }

  /** Path part for operation `deleteBlock()` */
  static readonly DeleteBlockPath = '/api/v1/vineyards/{vineyardId}/blocks/{blockId}';

  /**
   * Delete a block in a vineyard.
   *
   * Delete a block in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteBlock()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteBlock$Response(params: DeleteBlock$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deleteBlock(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete a block in a vineyard.
   *
   * Delete a block in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteBlock$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteBlock(params: DeleteBlock$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deleteBlock$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `getBlocks()` */
  static readonly GetBlocksPath = '/api/v1/vineyards/{vineyardId}/blocks';

  /**
   * Get all blocks in a vineyard.
   *
   * Get all blocks in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBlocks()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBlocks$Response(params: GetBlocks$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListBlockResponse>> {
    return getBlocks(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all blocks in a vineyard.
   *
   * Get all blocks in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBlocks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBlocks(params: GetBlocks$Params, context?: HttpContext): Observable<ApiResponsePageableResponseListBlockResponse> {
    return this.getBlocks$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponsePageableResponseListBlockResponse>): ApiResponsePageableResponseListBlockResponse => r.body)
    );
  }

  /** Path part for operation `CreateBlock()` */
  static readonly CreateBlockPath = '/api/v1/vineyards/{vineyardId}/blocks';

  /**
   * Create a new block in a vineyard.
   *
   * Create a new block in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createVineyard1()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVineyard1$Response(params: CreateBlock$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseBlockResponse>> {
    return createBlock(this.http, this.rootUrl, params, context);
  }

  /**
   * Create a new block in a vineyard.
   *
   * Create a new block in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createVineyard1$Response()` instead.
   *
   * This method sends `application/json` and handles the request body of type `application/json`.
   */
  createBlock(params: CreateBlock$Params, context?: HttpContext): Observable<ApiResponseBlockResponse> {
    return this.createVineyard1$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseBlockResponse>): ApiResponseBlockResponse => r.body)
    );
  }

  /** Path part for operation `generateBlockReport()` */
  static readonly GenerateBlockReportPath = '/api/v1/vineyards/{vineyardId}/blocks/{blockId}/_report';

  /**
   * Generate a report for a block in a vineyard.
   *
   * Generate a report for a block in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `generateBlockReport()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  generateBlockReport$Response(params: GenerateBlockReport$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<string>>> {
    return generateBlockReport(this.http, this.rootUrl, params, context);
  }

  /**
   * Generate a report for a block in a vineyard.
   *
   * Generate a report for a block in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `generateBlockReport$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  generateBlockReport(params: GenerateBlockReport$Params, context?: HttpContext): Observable<Array<string>> {
    return this.generateBlockReport$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<string>>): Array<string> => r.body)
    );
  }

}
