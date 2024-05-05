package com.vitisvision.vitisvisionservice.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect class to log exception handler methods
 */
@Aspect
@Component
public class ExceptionHandlerLoggingAspect extends BaseLoggingAspect {

    /**
     * Log exception handler classes methods
     *
     * @param joinPoint the join point to be processed
     * @return Object - the result of the method execution
     * @throws Throwable if an error occurs during method call execution or logging operation
     */
    @Around("exceptionHandlerMethods()")
    public Object logExceptionHandlerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, null);
    }

    /**
     * Log advisor classes methods
     *
     * @param joinPoint the join point to be processed
     * @return Object - the result of the method execution
     * @throws Throwable if an error occurs during method call execution or logging operation
     */
    @Around("advisorMethods()")
    public Object logAdvisorMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, null);
    }

}
