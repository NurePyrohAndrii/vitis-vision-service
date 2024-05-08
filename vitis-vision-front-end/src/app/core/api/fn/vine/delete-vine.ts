/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVoid } from '../../models/api-response-void';

export interface DeleteVine$Params {
  blockId: number;
  vineId: number;
}

export function deleteVine(http: HttpClient, rootUrl: string, params: DeleteVine$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
  const rb = new RequestBuilder(rootUrl, deleteVine.PATH, 'delete');
  if (params) {
    rb.path('blockId', params.blockId, {});
    rb.path('vineId', params.vineId, {});
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

deleteVine.PATH = '/api/v1/blocks/{blockId}/vines/{vineId}';
