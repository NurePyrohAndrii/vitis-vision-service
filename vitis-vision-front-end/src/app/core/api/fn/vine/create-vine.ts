/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVineResponse } from '../../models/api-response-vine-response';
import { VineRequest } from '../../models/vine-request';

export interface CreateVine$Params {
  blockId: number;
      body: VineRequest
}

export function createVine(http: HttpClient, rootUrl: string, params: CreateVine$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVineResponse>> {
  const rb = new RequestBuilder(rootUrl, createVine.PATH, 'post');
  if (params) {
    rb.path('blockId', params.blockId, {});
    rb.body(params.body, 'application/json');
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

createVine.PATH = '/api/v1/blocks/{blockId}/vines';
