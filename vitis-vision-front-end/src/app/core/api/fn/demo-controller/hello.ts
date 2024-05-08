/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseString } from '../../models/api-response-string';

export interface Hello$Params {
}

export function hello(http: HttpClient, rootUrl: string, params?: Hello$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseString>> {
  const rb = new RequestBuilder(rootUrl, hello.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseString>;
    })
  );
}

hello.PATH = '/api/v1/demo/hello';
