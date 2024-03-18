package com.vitisvision.vitisvisionservice.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse<T> {
    private final String status;
    private final int statusCode;
    private final List<ApiError> errors;
    private final T data;

    private ApiResponse(@JsonProperty("status") String status,
                        @JsonProperty("statusCode") int statusCode,
                        @JsonProperty("errors") List<ApiError> errors,
                        @JsonProperty("data") T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.errors = errors;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data, int statusCode) {
        return new ApiResponse<>("success", statusCode, null, data);
    }

    public static ApiResponse<List<ApiError>> error(List<ApiError> errors, int statusCode) {
        return new ApiResponse<>("error", statusCode, errors, null);
    }
}
