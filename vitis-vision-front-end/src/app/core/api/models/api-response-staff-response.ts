/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { StaffResponse } from '../models/staff-response';
export interface ApiResponseStaffResponse {
  data?: StaffResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
