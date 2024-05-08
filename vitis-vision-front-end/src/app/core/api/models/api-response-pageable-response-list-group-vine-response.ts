/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { PageableResponseListGroupVineResponse } from '../models/pageable-response-list-group-vine-response';
export interface ApiResponsePageableResponseListGroupVineResponse {
  data?: PageableResponseListGroupVineResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
