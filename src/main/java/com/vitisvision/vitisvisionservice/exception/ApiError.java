package com.vitisvision.vitisvisionservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiError {
        HttpStatus status;
        String message;
        String details;
        String timestamp;
}
