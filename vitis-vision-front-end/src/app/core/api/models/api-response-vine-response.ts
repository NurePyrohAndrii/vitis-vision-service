/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { VineResponse } from '../models/vine-response';
export interface ApiResponseVineResponse {
  data?: VineResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
