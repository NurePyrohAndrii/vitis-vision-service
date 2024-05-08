/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseDeviceResponse } from '../../models/api-response-device-response';
import { DeviceRequest } from '../../models/device-request';

export interface UpdateDevice$Params {
  vineId: number;
  deviceId: number;
      body: DeviceRequest
}

export function updateDevice(http: HttpClient, rootUrl: string, params: UpdateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceResponse>> {
  const rb = new RequestBuilder(rootUrl, updateDevice.PATH, 'put');
  if (params) {
    rb.path('vineId', params.vineId, {});
    rb.path('deviceId', params.deviceId, {});
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

updateDevice.PATH = '/api/v1/vines/{vineId}/devices/{deviceId}';
