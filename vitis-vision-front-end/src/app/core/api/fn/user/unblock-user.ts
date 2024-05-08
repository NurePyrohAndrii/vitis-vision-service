/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseVoid } from '../../models/api-response-void';
import { UserBlockRequest } from '../../models/user-block-request';

export interface UnblockUser$Params {
      body: UserBlockRequest
}

export function unblockUser(http: HttpClient, rootUrl: string, params: UnblockUser$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
  const rb = new RequestBuilder(rootUrl, unblockUser.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
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

unblockUser.PATH = '/api/v1/users/unblock';
