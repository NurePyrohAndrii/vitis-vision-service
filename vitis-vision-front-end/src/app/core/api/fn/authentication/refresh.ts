/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseAuthResponse } from '../../models/api-response-auth-response';

export interface Refresh$Params {
  Authorization: string;
}

export function refresh(http: HttpClient, rootUrl: string, params: Refresh$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseAuthResponse>> {
  const rb = new RequestBuilder(rootUrl, refresh.PATH, 'post');
  if (params) {
    rb.header('Authorization', params.Authorization, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseAuthResponse>;
    })
  );
}

refresh.PATH = '/api/v1/auth/refresh';
