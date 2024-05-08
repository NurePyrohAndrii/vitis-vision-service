/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { BlockResponse } from '../models/block-response';
export interface ApiResponseBlockResponse {
  data?: BlockResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
