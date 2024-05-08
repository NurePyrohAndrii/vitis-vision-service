/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVoid } from '../../models/api-response-void';

export interface DeactivateDevice$Params {
  vineId: number;
  deviceId: number;
}

export function deactivateDevice(http: HttpClient, rootUrl: string, params: DeactivateDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
  const rb = new RequestBuilder(rootUrl, deactivateDevice.PATH, 'post');
  if (params) {
    rb.path('vineId', params.vineId, {});
    rb.path('deviceId', params.deviceId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseVoid>;
    })
  );
}

deactivateDevice.PATH = '/api/v1/vines/{vineId}/devices/{deviceId}/deactivate';
