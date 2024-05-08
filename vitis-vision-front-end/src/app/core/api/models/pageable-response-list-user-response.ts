/* tslint:disable */
/* eslint-disable */
import { PageMetadata } from '../models/page-metadata';
import { UserResponse } from '../models/user-response';
export interface PageableResponseListUserResponse {
  content?: Array<UserResponse>;
  pageMetadata?: PageMetadata;
}
