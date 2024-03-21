package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.ChangePasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static com.vitisvision.vitisvisionservice.util.AdvisorUtils.createErrorResponseEntity;
import static com.vitisvision.vitisvisionservice.util.AdvisorUtils.getAnnotationResponseStatusCode;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserAdvisor {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<List<ApiError>>> handleChangePasswordException(ChangePasswordException e) {
        HttpStatus status = getAnnotationResponseStatusCode(e.getClass());

        List<ApiError> errors = List.of(
                new ApiError(
                        status,
                        e.getMessage(),
                        "Exception " + e.getClass().getSimpleName() + " was thrown",
                        LocalDateTime.now().toString()
                )
        );

        return createErrorResponseEntity(errors, status);
    }

}
