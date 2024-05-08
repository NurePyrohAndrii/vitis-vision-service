/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseBlockResponse } from '../../models/api-response-block-response';

export interface GetBlock$Params {
  vineyardId: number;
  blockId: number;
}

export function getBlock(http: HttpClient, rootUrl: string, params: GetBlock$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseBlockResponse>> {
  const rb = new RequestBuilder(rootUrl, getBlock.PATH, 'get');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.path('blockId', params.blockId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseBlockResponse>;
    })
  );
}

getBlock.PATH = '/api/v1/vineyards/{vineyardId}/blocks/{blockId}';
