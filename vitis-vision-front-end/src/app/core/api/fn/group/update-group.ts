/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseGroupResponse } from '../../models/api-response-group-response';
import { GroupRequest } from '../../models/group-request';

export interface UpdateGroup$Params {
  vineyardId: number;
  groupId: number;
      body: GroupRequest
}

export function updateGroup(http: HttpClient, rootUrl: string, params: UpdateGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseGroupResponse>> {
  const rb = new RequestBuilder(rootUrl, updateGroup.PATH, 'put');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.path('groupId', params.groupId, {});
    rb.body(params.body, 'application/json');
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

updateGroup.PATH = '/api/v1/vineyards/{vineyardId}/groups/{groupId}';
