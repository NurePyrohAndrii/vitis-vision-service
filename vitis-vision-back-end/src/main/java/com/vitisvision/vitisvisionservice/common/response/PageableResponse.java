package com.vitisvision.vitisvisionservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A generic class that is used to return the paged response of the API
 *
 * @param <T> The type of the data that is returned by the API.
 */
@Data
@AllArgsConstructor
public class PageableResponse<T> {
    T content;
    PageMetadata pageMetadata;

    /**
     * Static method to create a paged response from the page object.
     *
     * @param page The page object that is returned by the API.
     * @param <T>  The type of the data that is returned by the API.
     * @return The PageableResponse object.
     */
    public static <T> PageableResponse<List<T>> of(Page<T> page) {
        return new PageableResponse<>(
                page.getContent(),
                new PageMetadata(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalElements(),
                        page.getTotalPages()
                )
        );
    }
}
