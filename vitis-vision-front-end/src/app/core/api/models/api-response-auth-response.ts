/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { AuthResponse } from '../models/auth-response';
export interface ApiResponseAuthResponse {
  data?: AuthResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
