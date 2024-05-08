/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { BlockReportRequest } from '../../models/block-report-request';

export interface GenerateBlockReport$Params {
  vineyardId: number;
  blockId: number;
      body: BlockReportRequest
}

export function generateBlockReport(http: HttpClient, rootUrl: string, params: GenerateBlockReport$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<string>>> {
  const rb = new RequestBuilder(rootUrl, generateBlockReport.PATH, 'post');
  if (params) {
    rb.path('vineyardId', params.vineyardId, {});
    rb.path('blockId', params.blockId, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: 'application/octet-stream', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<string>>;
    })
  );
}

generateBlockReport.PATH = '/api/v1/vineyards/{vineyardId}/blocks/{blockId}/_report';
