/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ApiResponseListDbBackupResponse } from '../../models/api-response-list-db-backup-response';

export interface GetBackups$Params {
}

export function getBackups(http: HttpClient, rootUrl: string, params?: GetBackups$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseListDbBackupResponse>> {
  const rb = new RequestBuilder(rootUrl, getBackups.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ApiResponseListDbBackupResponse>;
    })
  );
}

getBackups.PATH = '/api/v1/db-backups';
