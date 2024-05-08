/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { UserResponse } from '../models/user-response';
export interface ApiResponseUserResponse {
  data?: UserResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
