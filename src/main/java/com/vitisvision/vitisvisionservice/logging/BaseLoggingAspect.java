package com.vitisvision.vitisvisionservice.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Base aspect class to define common pointcuts for logging aspects
 */
@Aspect
public class BaseLoggingAspect {

    /**
     * Pointcut for all repository methods
     */
    @Pointcut("execution(* com.vitisvision.vitisvisionservice.*.*Repository.*(..))")
    public void repositoryMethods() {
    }

    /**
     * Pointcut for all service methods
     */
    @Pointcut("execution(* (@org.springframework.stereotype.Service *).*(..))")
    public void serviceMethods() {
    }

    /**
     * Pointcut for all controller methods
     */
    @Pointcut("execution(* (@org.springframework.web.bind.annotation.RestController *).*(..))")
    public void restControllerMethods() {
    }

    /**
     * Pointcut for all once per request filter methods
     */
    @Pointcut("execution(* org.springframework.web.filter.OncePerRequestFilter.*(..))")
    public void oncePerRequestFilterMethods() {
    }

    /**
     * Pointcut for all exception handler methods
     */
    @Pointcut("execution(* com.vitisvision.vitisvisionservice.*.*ExceptionHandler.*(..))")
    public void exceptionHandlerMethods() {
    }

    /**
     * Pointcut for all controller advisor methods
     */
    @Pointcut("execution(* com.vitisvision.vitisvisionservice.*.*Advisor.*(..))")
    public void advisorMethods() {
    }
}
