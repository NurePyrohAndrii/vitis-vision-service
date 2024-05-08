/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseDeviceActivateResponse } from '../../models/api-response-device-activate-response';

export interface ActivateDevice$Params {
  vineId: number;
  deviceId: number;
  frequency: number;
}

export function activateDevice(http: HttpClient, rootUrl: string, params: ActivateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceActivateResponse>> {
  const rb = new RequestBuilder(rootUrl, activateDevice.PATH, 'post');
  if (params) {
    rb.path('vineId', params.vineId, {});
    rb.path('deviceId', params.deviceId, {});
    rb.path('frequency', params.frequency, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseDeviceActivateResponse>;
    })
  );
}

activateDevice.PATH = '/api/v1/vines/{vineId}/devices/{deviceId}/activate/{frequency}';
