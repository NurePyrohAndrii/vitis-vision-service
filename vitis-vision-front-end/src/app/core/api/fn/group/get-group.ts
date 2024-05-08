/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseGroupResponse } from '../../models/api-response-group-response';

export interface GetGroup$Params {
  vineyardId: number;
  groupId: number;
}

export function getGroup(http: HttpClient, rootUrl: string, params: GetGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseGroupResponse>> {
  const rb = new RequestBuilder(rootUrl, getGroup.PATH, 'get');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.path('groupId', params.groupId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseGroupResponse>;
    })
  );
}

getGroup.PATH = '/api/v1/vineyards/{vineyardId}/groups/{groupId}';
