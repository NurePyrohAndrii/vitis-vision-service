/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { activateDevice } from '../fn/device/activate-device';
import { ActivateDevice$Params } from '../fn/device/activate-device';
import { ApiResponseDeviceActivateResponse } from '../models/api-response-device-activate-response';
import { ApiResponseDeviceResponse } from '../models/api-response-device-response';
import { ApiResponseVoid } from '../models/api-response-void';
import { createDevice } from '../fn/device/create-device';
import { CreateDevice$Params } from '../fn/device/create-device';
import { deactivateDevice } from '../fn/device/deactivate-device';
import { DeactivateDevice$Params } from '../fn/device/deactivate-device';
import { deleteDevice } from '../fn/device/delete-device';
import { DeleteDevice$Params } from '../fn/device/delete-device';
import { getDevice } from '../fn/device/get-device';
import { GetDevice$Params } from '../fn/device/get-device';
import { updateDevice } from '../fn/device/update-device';
import { UpdateDevice$Params } from '../fn/device/update-device';


/**
 * Endpoint for device details management in a vineyard
 */
@Injectable({ providedIn: 'root' })
export class DeviceService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getDevice()` */
  static readonly GetDevicePath = '/api/v1/vines/{vineId}/devices';

  /**
   * Get the device details by id.
   *
   * Get the device details by id in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getDevice()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDevice$Response(params: GetDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceResponse>> {
    return getDevice(this.http, this.rootUrl, params, context);
  }

  /**
   * Get the device details by id.
   *
   * Get the device details by id in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getDevice$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDevice(params: GetDevice$Params, context?: HttpContext): Observable<ApiResponseDeviceResponse> {
    return this.getDevice$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseDeviceResponse>): ApiResponseDeviceResponse => r.body)
    );
  }

  /** Path part for operation `updateDevice()` */
  static readonly UpdateDevicePath = '/api/v1/vines/{vineId}/devices/{deviceId}';

  /**
   * Update the device details.
   *
   * Update the device details in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateDevice()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateDevice$Response(params: UpdateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceResponse>> {
    return updateDevice(this.http, this.rootUrl, params, context);
  }

  /**
   * Update the device details.
   *
   * Update the device details in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateDevice$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateDevice(params: UpdateDevice$Params, context?: HttpContext): Observable<ApiResponseDeviceResponse> {
    return this.updateDevice$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseDeviceResponse>): ApiResponseDeviceResponse => r.body)
    );
  }

  /** Path part for operation `deleteDevice()` */
  static readonly DeleteDevicePath = '/api/v1/vines/{vineId}/devices/{deviceId}';

  /**
   * Delete the device by id.
   *
   * Delete the device by id in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteDevice()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteDevice$Response(params: DeleteDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deleteDevice(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete the device by id.
   *
   * Delete the device by id in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteDevice$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteDevice(params: DeleteDevice$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deleteDevice$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `createDevice()` */
  static readonly CreateDevicePath = '/api/v1/vines/{vineId}/devices';

  /**
   * Create a new device in a vine.
   *
   * Create a new device in a vine with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createDevice()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createDevice$Response(params: CreateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceResponse>> {
    return createDevice(this.http, this.rootUrl, params, context);
  }

  /**
   * Create a new device in a vine.
   *
   * Create a new device in a vine with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createDevice$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createDevice(params: CreateDevice$Params, context?: HttpContext): Observable<ApiResponseDeviceResponse> {
    return this.createDevice$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseDeviceResponse>): ApiResponseDeviceResponse => r.body)
    );
  }

  /** Path part for operation `deactivateDevice()` */
  static readonly DeactivateDevicePath = '/api/v1/vines/{vineId}/devices/{deviceId}/deactivate';

  /**
   * Deactivate the device.
   *
   * Deactivate the device in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deactivateDevice()` instead.
   *
   * This method doesn't expect any request body.
   */
  deactivateDevice$Response(params: DeactivateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return deactivateDevice(this.http, this.rootUrl, params, context);
  }

  /**
   * Deactivate the device.
   *
   * Deactivate the device in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deactivateDevice$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deactivateDevice(params: DeactivateDevice$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.deactivateDevice$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

  /** Path part for operation `activateDevice()` */
  static readonly ActivateDevicePath = '/api/v1/vines/{vineId}/devices/{deviceId}/activate/{frequency}';

  /**
   * Activate the device with the provided frequency.
   *
   * Activate the device with the provided frequency in a vineyard with the provided details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `activateDevice()` instead.
   *
   * This method doesn't expect any request body.
   */
  activateDevice$Response(params: ActivateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceActivateResponse>> {
    return activateDevice(this.http, this.rootUrl, params, context);
  }

  /**
   * Activate the device with the provided frequency.
   *
   * Activate the device with the provided frequency in a vineyard with the provided details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `activateDevice$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  activateDevice(params: ActivateDevice$Params, context?: HttpContext): Observable<ApiResponseDeviceActivateResponse> {
    return this.activateDevice$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseDeviceActivateResponse>): ApiResponseDeviceActivateResponse => r.body)
    );
  }

}
