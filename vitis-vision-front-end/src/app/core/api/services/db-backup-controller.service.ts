/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ApiResponseListDbBackupResponse } from '../models/api-response-list-db-backup-response';
import { ApiResponseVoid } from '../models/api-response-void';
import { backupDatabase } from '../fn/db-backup-controller/backup-database';
import { BackupDatabase$Params } from '../fn/db-backup-controller/backup-database';
import { getBackups } from '../fn/db-backup-controller/get-backups';
import { GetBackups$Params } from '../fn/db-backup-controller/get-backups';

@Injectable({ providedIn: 'root' })
export class DbBackupControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getBackups()` */
  static readonly GetBackupsPath = '/api/v1/db-backups';

  /**
   * Get backups.
   *
   * Get a list of file names of available backups in the configured folder
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBackups()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBackups$Response(params?: GetBackups$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseListDbBackupResponse>> {
    return getBackups(this.http, this.rootUrl, params, context);
  }

  /**
   * Get backups.
   *
   * Get a list of file names of available backups in the configured folder
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBackups$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBackups(params?: GetBackups$Params, context?: HttpContext): Observable<ApiResponseListDbBackupResponse> {
    return this.getBackups$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseListDbBackupResponse>): ApiResponseListDbBackupResponse => r.body)
    );
  }

  /** Path part for operation `backupDatabase()` */
  static readonly BackupDatabasePath = '/api/v1/db-backups';

  /**
   * Backup database.
   *
   * Backup database and store the result in the configured folder
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `backupDatabase()` instead.
   *
   * This method doesn't expect any request body.
   */
  backupDatabase$Response(params?: BackupDatabase$Params, context?: HttpContext): Observable<StrictHttpResponse<ApiResponseVoid>> {
    return backupDatabase(this.http, this.rootUrl, params, context);
  }

  /**
   * Backup database.
   *
   * Backup database and store the result in the configured folder
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `backupDatabase$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  backupDatabase(params?: BackupDatabase$Params, context?: HttpContext): Observable<ApiResponseVoid> {
    return this.backupDatabase$Response(params, context).pipe(
      map((r: StrictHttpResponse<ApiResponseVoid>): ApiResponseVoid => r.body)
    );
  }

}
