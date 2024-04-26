package com.vitisvision.vitisvisionservice.domain.vinayard.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.controller.vineyard.VineyardController;
import com.vitisvision.vitisvisionservice.controller.vineyard.block.BlockController;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardDuplicationException;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardNotFoundException;
import com.vitisvision.vitisvisionservice.domain.vinayard.exception.VineyardParticipationConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * VineyardAdvisor class is an exception handler for the VineyardController.
 */
@ControllerAdvice(assignableTypes = {VineyardController.class, BlockController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class VineyardAdvisor {

    /**
     * The AdvisorUtils object that contains utility methods.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handle the VineyardParticipationConflictException that occurs when a user tries to participate in a vineyard while already participating in the vineyard.
     *
     * @param e the exception object of type {@link VineyardParticipationConflictException}
     * @return the response entity with the error message and status code
     */
    @ExceptionHandler(VineyardParticipationConflictException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleVineyardParticipationConflictException(VineyardParticipationConflictException e) {
        return advisorUtils.createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }
}
