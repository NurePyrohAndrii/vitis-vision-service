/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { PageableResponseListUserResponse } from '../models/pageable-response-list-user-response';
export interface ApiResponsePageableResponseListUserResponse {
  data?: PageableResponseListUserResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
