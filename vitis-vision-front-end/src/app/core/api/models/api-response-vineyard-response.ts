/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { VineyardResponse } from '../models/vineyard-response';
export interface ApiResponseVineyardResponse {
  data?: VineyardResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
