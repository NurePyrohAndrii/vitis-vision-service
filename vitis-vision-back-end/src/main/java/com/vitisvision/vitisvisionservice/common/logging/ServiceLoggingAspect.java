package com.vitisvision.vitisvisionservice.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service methods.
 */
@Aspect
@Component
public class ServiceLoggingAspect extends BaseLoggingAspect {

    /**
     * Logs service methods.
     * @param joinPoint ProceedingJoinPoint object representing the method call being intercepted
     * @return Object result of the method call
     * @throws Throwable if an error occurs during method call execution or logging operation
     * @see org.aspectj.lang.ProceedingJoinPoint
     */
    @Around("serviceMethods()")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return  logMethodExecution(joinPoint, null);
    }

}
