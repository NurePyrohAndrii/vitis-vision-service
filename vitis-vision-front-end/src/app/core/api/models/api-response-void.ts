/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
export interface ApiResponseVoid {
  data?: {
};
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
