/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { PageableResponseListVineResponse } from '../models/pageable-response-list-vine-response';
export interface ApiResponsePageableResponseListVineResponse {
  data?: PageableResponseListVineResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
