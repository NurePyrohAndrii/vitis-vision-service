/* tslint:disable */
/* eslint-disable */
import { ApiError } from './api-error';
import { PageableResponseListVineResponse } from './pageable-response-list-vine-response';
export interface ApiResponsePageableResponseListVineResponse {
  data?: PageableResponseListVineResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
