/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponsePageableResponseListGroupVineResponse } from '../../models/api-response-pageable-response-list-group-vine-response';
import { Pageable } from '../../models/pageable';

export interface GetVinesInGroup$Params {
  vineyardId: number;
  groupId: number;
  pageable: Pageable;
}

export function getVinesInGroup(http: HttpClient, rootUrl: string, params: GetVinesInGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListGroupVineResponse>> {
  const rb = new RequestBuilder(rootUrl, getVinesInGroup.PATH, 'get');
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

getVinesInGroup.PATH = '/api/v1/vineyards/{vineyardId}/groups/{groupId}/vines';
