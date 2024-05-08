/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVoid } from '../../models/api-response-void';

export interface DeleteBlock$Params {
  vineyardId: number;
  blockId: number;
}

export function deleteBlock(http: HttpClient, rootUrl: string, params: DeleteBlock$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
  const rb = new RequestBuilder(rootUrl, deleteBlock.PATH, 'delete');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.path('blockId', params.blockId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseVoid>;
    })
  );
}

deleteBlock.PATH = '/api/v1/vineyards/{vineyardId}/blocks/{blockId}';
