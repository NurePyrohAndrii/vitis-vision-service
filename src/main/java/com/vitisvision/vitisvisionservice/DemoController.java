package com.vitisvision.vitisvisionservice;

import com.vitisvision.vitisvisionservice.exception.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<?>> hello() {
        return ResponseEntity.ok(ApiResponse.success("Hello, World!", HttpStatus.OK.value()));
    }
}
