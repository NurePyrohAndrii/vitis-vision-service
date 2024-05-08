/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { DbBackupResponse } from '../models/db-backup-response';
export interface ApiResponseListDbBackupResponse {
  data?: Array<DbBackupResponse>;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
