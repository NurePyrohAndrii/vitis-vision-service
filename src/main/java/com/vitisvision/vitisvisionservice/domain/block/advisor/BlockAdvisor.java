package com.vitisvision.vitisvisionservice.domain.block.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.controller.vineyard.block.BlockController;
import com.vitisvision.vitisvisionservice.domain.block.exception.BlockDuplicationException;
import com.vitisvision.vitisvisionservice.domain.block.exception.BlockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * The type Block advisor is used to handle exceptions thrown by the block controller.
 */
@ControllerAdvice(assignableTypes = BlockController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class BlockAdvisor {

    /**
     * The Advisor utils is used to handle common advisor operations.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handle block not found exception.
     *
     * @param e the exception object of type {@link BlockNotFoundException}
     * @return the response entity with the error message and status code
     */
    @ExceptionHandler(BlockNotFoundException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleBlockNotFoundException(BlockNotFoundException e) {
        return advisorUtils.createErrorResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle block duplication exception.
     *
     * @param e the exception object of type {@link BlockDuplicationException}
     * @return the response entity with the error message and status code
     */
    @ExceptionHandler(BlockDuplicationException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleBlockDuplicationException(BlockDuplicationException e) {
        return advisorUtils.createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

}
