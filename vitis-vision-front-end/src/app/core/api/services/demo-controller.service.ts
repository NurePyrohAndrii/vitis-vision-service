/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponseString } from '../models/api-response-string';
import { hello } from '../fn/demo-controller/hello';
import { Hello$Params } from '../fn/demo-controller/hello';

@Injectable({ providedIn: 'root' })
export class DemoControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `hello()` */
  static readonly HelloPath = '/api/v1/demo/hello';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `hello()` instead.
   *
   * This method doesn't expect any request body.
   */
  hello$Response(params?: Hello$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseString>> {
    return hello(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `hello$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  hello(params?: Hello$Params, context?: HttpContext): Observable<ApiResponseString> {
    return this.hello$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseString>): ApiResponseString => r.body)
    );
  }

}
