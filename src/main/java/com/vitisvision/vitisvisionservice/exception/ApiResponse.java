package com.vitisvision.vitisvisionservice.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse<T> {
    private final String status;
    private final int statusCode;
    private final T errors;
    private final T data;

    private ApiResponse(String status, int statusCode, T errors, T data) {
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
