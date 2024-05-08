/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseDeviceResponse } from '../../models/api-response-device-response';
import { DeviceRequest } from '../../models/device-request';

export interface CreateDevice$Params {
  vineId: number;
      body: DeviceRequest
}

export function createDevice(http: HttpClient, rootUrl: string, params: CreateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceResponse>> {
  const rb = new RequestBuilder(rootUrl, createDevice.PATH, 'post');
  if (params) {
    rb.path('vineId', params.vineId, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseDeviceResponse>;
    })
  );
}

createDevice.PATH = '/api/v1/vines/{vineId}/devices';
