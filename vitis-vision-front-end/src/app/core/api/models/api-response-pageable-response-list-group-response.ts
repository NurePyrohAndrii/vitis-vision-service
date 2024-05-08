/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { PageableResponseListGroupResponse } from '../models/pageable-response-list-group-response';
export interface ApiResponsePageableResponseListGroupResponse {
  data?: PageableResponseListGroupResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
