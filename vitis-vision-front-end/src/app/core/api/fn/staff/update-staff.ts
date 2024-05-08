/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseStaffResponse } from '../../models/api-response-staff-response';
import { StaffRequest } from '../../models/staff-request';

export interface UpdateStaff$Params {
  vineyardId: number;
      body: StaffRequest
}

export function updateStaff(http: HttpClient, rootUrl: string, params: UpdateStaff$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseStaffResponse>> {
  const rb = new RequestBuilder(rootUrl, updateStaff.PATH, 'put');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseStaffResponse>;
    })
  );
}

updateStaff.PATH = '/api/v1/vineyards/{vineyardId}/staff';
