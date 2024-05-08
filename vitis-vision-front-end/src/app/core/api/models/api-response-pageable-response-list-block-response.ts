/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { PageableResponseListBlockResponse } from '../models/pageable-response-list-block-response';
export interface ApiResponsePageableResponseListBlockResponse {
  data?: PageableResponseListBlockResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
