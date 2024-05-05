package com.vitisvision.vitisvisionservice.user.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.controller.user.UserController;
import com.vitisvision.vitisvisionservice.user.exception.ChangePasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        return advisorUtils.createErrorResponseEntity(
                e,
                advisorUtils.getAnnotationResponseStatusCode(e.getClass())
        );
    }

}
