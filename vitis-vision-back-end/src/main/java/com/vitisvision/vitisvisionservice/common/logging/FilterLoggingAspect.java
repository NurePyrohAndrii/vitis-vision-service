package com.vitisvision.vitisvisionservice.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging security filter methods.
 */
@Aspect
@Component
public class FilterLoggingAspect extends BaseLoggingAspect{

    /**
     * Log around filter method call.
     *
     * @param pjp  ProceedingJoinPoint - Join point for the filter method.
     * @return Object - Result of the filter method call.
     * @throws Throwable - Exception thrown during proceeding the join point.
     */
    @Around("mdcFilterMethods()")
    public Object logRepositoryMethods(ProceedingJoinPoint pjp) throws Throwable {
        return logMethodExecution(pjp, null);
    }
}
