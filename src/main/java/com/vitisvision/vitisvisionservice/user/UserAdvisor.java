package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.common.api.ApiError;
import com.vitisvision.vitisvisionservice.common.api.ApiResponse;
import com.vitisvision.vitisvisionservice.common.exception.ChangePasswordException;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserAdvisor class is a controller advice class that handles exceptions thrown by the UserController class.
 */
@ControllerAdvice(assignableTypes = UserController.class)
@RequiredArgsConstructor
public class UserAdvisor {

    /**
     * AdvisorUtils object that contains utility methods.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handles ChangePasswordException exception.
     *
     * @param e ChangePasswordException object to be handled
     * @return ResponseEntity<ApiResponse<List<ApiError> response entity with a list of ApiError objects
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<List<ApiError>>> handleChangePasswordException(ChangePasswordException e) {
        HttpStatus status = advisorUtils.getAnnotationResponseStatusCode(e.getClass());

        List<ApiError> errors = List.of(
                new ApiError(
                        status,
                        advisorUtils.getErrorMessageString(e),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );

        return advisorUtils.createErrorResponseEntity(errors, status);
    }

}
