/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponsePageableResponseListStaffResponse } from '../../models/api-response-pageable-response-list-staff-response';
import { Pageable } from '../../models/pageable';

export interface GetAllStaff$Params {
  vineyardId: number;
  pageable: Pageable;
}

export function getAllStaff(http: HttpClient, rootUrl: string, params: GetAllStaff$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListStaffResponse>> {
  const rb = new RequestBuilder(rootUrl, getAllStaff.PATH, 'get');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.query('pageable', params.pageable, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponsePageableResponseListStaffResponse>;
    })
  );
}

getAllStaff.PATH = '/api/v1/vineyards/{vineyardId}/staff';
