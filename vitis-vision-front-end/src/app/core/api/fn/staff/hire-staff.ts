/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVoid } from '../../models/api-response-void';
import { StaffRequest } from '../../models/staff-request';

export interface HireStaff$Params {
  vineyardId: number;
      body: StaffRequest
}

export function hireStaff(http: HttpClient, rootUrl: string, params: HireStaff$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
  const rb = new RequestBuilder(rootUrl, hireStaff.PATH, 'post');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.body(params.body, 'application/json');
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

hireStaff.PATH = '/api/v1/vineyards/{vineyardId}/staff';
