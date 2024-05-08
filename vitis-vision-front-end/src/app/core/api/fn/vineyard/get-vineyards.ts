/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponsePageableResponseListVineyardResponse } from '../../models/api-response-pageable-response-list-vineyard-response';
import { Pageable } from '../../models/pageable';

export interface GetVineyards$Params {
  pageable: Pageable;
}

export function getVineyards(http: HttpClient, rootUrl: string, params: GetVineyards$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponsePageableResponseListVineyardResponse>> {
  const rb = new RequestBuilder(rootUrl, getVineyards.PATH, 'get');
  if (params) {
    rb.query('pageable', params.pageable, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponsePageableResponseListVineyardResponse>;
    })
  );
}

getVineyards.PATH = '/api/v1/vineyards';
