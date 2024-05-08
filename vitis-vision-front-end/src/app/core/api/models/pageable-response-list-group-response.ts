/* tslint:disable */
/* eslint-disable */
import { GroupResponse } from '../models/group-response';
import { PageMetadata } from '../models/page-metadata';
export interface PageableResponseListGroupResponse {
  content?: Array<GroupResponse>;
  pageMetadata?: PageMetadata;
}
