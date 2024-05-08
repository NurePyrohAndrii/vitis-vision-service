/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVineResponse } from '../../models/api-response-vine-response';

export interface GetVine$Params {
  blockId: number;
  vineId: number;
}

export function getVine(http: HttpClient, rootUrl: string, params: GetVine$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineResponse>> {
  const rb = new RequestBuilder(rootUrl, getVine.PATH, 'get');
  if (params) {
    rb.path('blockId', params.blockId, {});
    rb.path('vineId', params.vineId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseVineResponse>;
    })
  );
}

getVine.PATH = '/api/v1/blocks/{blockId}/vines/{vineId}';
