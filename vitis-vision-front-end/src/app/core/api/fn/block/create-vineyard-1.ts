/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseBlockResponse } from '../../models/api-response-block-response';
import { BlockRequest } from '../../models/block-request';

export interface CreateBlock$Params {
  vineyardId: number;
      body: BlockRequest
}

export function createBlock(http: HttpClient, rootUrl: string, params: CreateBlock$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseBlockResponse>> {
  const rb = new RequestBuilder(rootUrl, createBlock.PATH, 'post');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.body(params.body, 'application/json');
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

createBlock.PATH = '/api/v1/vineyards/{vineyardId}/blocks';
