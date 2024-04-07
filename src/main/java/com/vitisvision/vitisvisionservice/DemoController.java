package com.vitisvision.vitisvisionservice;

import com.vitisvision.vitisvisionservice.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

    private final MessageSource messageSource;

    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<?>> hello(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale
    ) {
        return ResponseEntity.ok(ApiResponse.success(messageSource.getMessage("validation.error", null, locale), HttpStatus.OK.value()));
    }
}
