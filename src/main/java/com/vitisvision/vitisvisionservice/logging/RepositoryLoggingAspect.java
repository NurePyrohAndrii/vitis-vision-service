package com.vitisvision.vitisvisionservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * Aspect for logging repository methods.
 */
@Aspect
@Component
@Slf4j
public class RepositoryLoggingAspect extends BaseLoggingAspect {

    /**
     * Log before repository method call.
     *
     * @param pjp  ProceedingJoinPoint - Join point for the repository method.
     * @param bean Object - Repository object.
     * @return Object - Result of the repository method call.
     * @throws Throwable - Exception thrown during proceeding the join point.
     */
    @Around("repositoryMethods() && target(bean)")
    public Object logRepositoryMethods(ProceedingJoinPoint pjp, Object bean) throws Throwable {
        return logMethodExecution(pjp, bean);
    }
}
