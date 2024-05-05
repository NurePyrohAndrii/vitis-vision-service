package com.vitisvision.vitisvisionservice.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging repository methods.
 */
@Aspect
@Component
public class RepositoryLoggingAspect extends BaseLoggingAspect {

    /**
     * Log before user and token repository method call.
     *
     * @param pjp  ProceedingJoinPoint - Join point for the user and token repository method.
     * @param bean Object - Repository object.
     * @return Object - Result of the repository method call.
     * @throws Throwable - Exception thrown during proceeding the join point.
     */
    @Around("userAndTokenRepositoriesMethods() && target(bean)")
    public Object logUserAndTokenRepositoryMethods(ProceedingJoinPoint pjp, Object bean) throws Throwable {
        return logMethodExecution(pjp, bean);
    }

    /**
     * Log before domain repository method call.
     *
     * @param pjp  ProceedingJoinPoint - Join point for the domain repository method.
     * @param bean Object - Domain repository object.
     * @return Object - Result of the domain repository method call.
     * @throws Throwable - Exception thrown during proceeding the join point.
     */
    @Around("domainRepositories() && target(bean)")
    public Object logDomainRepositoryMethods(ProceedingJoinPoint pjp, Object bean) throws Throwable {
        return logMethodExecution(pjp, bean);
    }
}
