package com.vitisvision.vitisvisionservice.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BaseLoggingAspect {

    // annotated with @Service
    @Pointcut("execution(* (@org.springframework.stereotype.Service *).*(..))")
    public void serviceMethods() {
    }

    // annotated with @RestController
    @Pointcut("execution(* (@org.springframework.web.bind.annotation.RestController *).*(..))")
    public void restControllerMethods() {
    }

    // OnePerRequestFilter
    @Pointcut("execution(* org.springframework.web.filter.OncePerRequestFilter.*(..))")
    public void oncePerRequestFilterMethods() {
    }
}
