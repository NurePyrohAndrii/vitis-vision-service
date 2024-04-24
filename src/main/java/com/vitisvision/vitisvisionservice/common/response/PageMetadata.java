package com.vitisvision.vitisvisionservice.common.response;

/**
 * PageMetadata record class to hold the page metadata information such as page number, page size, total elements and total pages.
 */
public record PageMetadata(int page, int size, long totalElements, int totalPages) {
}