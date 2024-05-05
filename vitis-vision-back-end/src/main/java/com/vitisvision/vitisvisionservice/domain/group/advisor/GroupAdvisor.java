package com.vitisvision.vitisvisionservice.domain.group.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.controller.vineyard.group.GroupController;
import com.vitisvision.vitisvisionservice.domain.group.exception.VinesGroupAssignmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GroupAdvisor class is an advisor class for handling group related exceptions.
 */
@ControllerAdvice(assignableTypes = GroupController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class GroupAdvisor {

    /**
     * Utility class for handling advisor operations
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handle VinesGroupAssignmentException and return the response entity with the error details.
     *
     * @param exception the exception object
     * @return the response entity with the error details
     */
    @ExceptionHandler(VinesGroupAssignmentException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleVinesGroupAssignmentException(VinesGroupAssignmentException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ApiError> errors = List.of(
                ApiError.builder()
                        .status(status)
                        .message(exception.getMessage())
                        .details(advisorUtils.getErrorDetailsString(exception))
                        .timestamp(LocalDateTime.now().toString())
                        .build()
        );
        return advisorUtils.createErrorResponseEntity(errors, status);
    }
}
