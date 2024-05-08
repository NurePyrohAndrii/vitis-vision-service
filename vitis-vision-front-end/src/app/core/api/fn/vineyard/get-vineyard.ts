/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVineyardResponse } from '../../models/api-response-vineyard-response';

export interface GetVineyard$Params {
  vineyardId: number;
}

export function getVineyard(http: HttpClient, rootUrl: string, params: GetVineyard$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineyardResponse>> {
  const rb = new RequestBuilder(rootUrl, getVineyard.PATH, 'get');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseVineyardResponse>;
    })
  );
}

getVineyard.PATH = '/api/v1/vineyards/{vineyardId}';
