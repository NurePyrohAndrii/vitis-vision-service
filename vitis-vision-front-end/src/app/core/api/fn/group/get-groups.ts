/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponsePageableResponseListGroupResponse } from '../../models/api-response-pageable-response-list-group-response';
import { Pageable } from '../../models/pageable';

export interface GetGroups$Params {
  pageable: Pageable;
  vineyardId: number;
}

export function getGroups(http: HttpClient, rootUrl: string, params: GetGroups$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListGroupResponse>> {
  const rb = new RequestBuilder(rootUrl, getGroups.PATH, 'get');
  if (params) {
    rb.query('pageable', params.pageable, {});
    rb.path('vineyardId', params.vineyardId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponsePageableResponseListGroupResponse>;
    })
  );
}

getGroups.PATH = '/api/v1/vineyards/{vineyardId}/groups';
