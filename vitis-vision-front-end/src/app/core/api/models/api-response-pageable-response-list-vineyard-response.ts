/* tslint:disable */
/* eslint-disable */
import { ApiError } from '../models/api-error';
import { PageableResponseListVineyardResponse } from '../models/pageable-response-list-vineyard-response';
export interface ApiResponsePageableResponseListVineyardResponse {
  data?: PageableResponseListVineyardResponse;
  errors?: Array<ApiError>;
  status?: string;
  statusCode?: number;
}
