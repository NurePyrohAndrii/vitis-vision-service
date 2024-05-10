/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponsePageableResponseListGroupVineResponse } from '../../models/api-response-pageable-response-list-group-vine-response';
import { Pageable } from '../../models/pageable';

export interface GetVinesToAssign$Params {
  vineyardId: number;
  groupId: number;
  pageable: Pageable;
}

export function getVinesToAssign(http: HttpClient, rootUrl: string, params: GetVinesToAssign$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListGroupVineResponse>> {
  const rb = new RequestBuilder(rootUrl, getVinesToAssign.PATH, 'get');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.path('groupId', params.groupId, {});
    rb.query('pageable', params.pageable, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponsePageableResponseListGroupVineResponse>;
    })
  );
}

getVinesToAssign.PATH = '/api/v1/vineyards/{vineyardId}/groups/{groupId}/_vines_to_assign';
