/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { GroupResponse } from '../models/group-response';
export interface ApiResponseGroupResponse {
  data?: GroupResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
