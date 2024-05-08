/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { DeviceResponse } from '../models/device-response';
export interface ApiResponseDeviceResponse {
  data?: DeviceResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
