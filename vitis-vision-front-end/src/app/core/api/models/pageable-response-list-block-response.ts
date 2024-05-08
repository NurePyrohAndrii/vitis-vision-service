/* tslint:disable */
/* eslint-disable */
import { BlockResponse } from '../models/block-response';
import { PageMetadata } from '../models/page-metadata';
export interface PageableResponseListBlockResponse {
  content?: Array<BlockResponse>;
  pageMetadata?: PageMetadata;
}
