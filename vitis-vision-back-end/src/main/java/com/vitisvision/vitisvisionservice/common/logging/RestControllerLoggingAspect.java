package com.vitisvision.vitisvisionservice.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of rest controllers.
 */
@Aspect
@Component
public class RestControllerLoggingAspect extends BaseLoggingAspect {

    /**
     * Log all rest controller methods.
     *
     * @param joinPoint the join point to log
     * @return the object returned by the method
     * @throws Throwable the throwable thrown by the method
     */
    @Around("restControllerMethods()")
    public Object logRestControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, null);
    }
}
