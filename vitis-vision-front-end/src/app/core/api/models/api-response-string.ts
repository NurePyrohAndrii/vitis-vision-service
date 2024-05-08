/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
export interface ApiResponseString {
  data?: string;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
