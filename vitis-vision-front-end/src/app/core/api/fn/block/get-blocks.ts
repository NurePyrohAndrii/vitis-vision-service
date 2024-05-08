/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponsePageableResponseListBlockResponse } from '../../models/api-response-pageable-response-list-block-response';
import { Pageable } from '../../models/pageable';

export interface GetBlocks$Params {
  pageable: Pageable;
  vineyardId: number;
}

export function getBlocks(http: HttpClient, rootUrl: string, params: GetBlocks$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListBlockResponse>> {
  const rb = new RequestBuilder(rootUrl, getBlocks.PATH, 'get');
  if (params) {
    rb.query('pageable', params.pageable, {});
    rb.path('vineyardId', params.vineyardId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponsePageableResponseListBlockResponse>;
    })
  );
}

getBlocks.PATH = '/api/v1/vineyards/{vineyardId}/blocks';
