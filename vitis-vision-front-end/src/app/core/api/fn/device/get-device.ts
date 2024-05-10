/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseDeviceResponse } from '../../models/api-response-device-response';

export interface GetDevice$Params {
  vineId: number;
}

export function getDevice(http: HttpClient, rootUrl: string, params: GetDevice$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseDeviceResponse>> {
  const rb = new RequestBuilder(rootUrl, getDevice.PATH, 'get');
  if (params) {
    rb.path('vineId', params.vineId, {});
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

getDevice.PATH = '/api/v1/vines/{vineId}/devices';
