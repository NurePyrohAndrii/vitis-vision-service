/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { DeviceActivateResponse } from '../models/device-activate-response';
export interface ApiResponseDeviceActivateResponse {
  data?: DeviceActivateResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
