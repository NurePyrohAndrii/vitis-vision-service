/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { PageableResponseListStaffResponse } from '../models/pageable-response-list-staff-response';
export interface ApiResponsePageableResponseListStaffResponse {
  data?: PageableResponseListStaffResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
