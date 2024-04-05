package com.vitisvision.vitisvisionservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.framework.Advised;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging repository methods.
 */
@Aspect
@Component
@Slf4j
public class RepositoryLoggingAspect extends BaseLoggingAspect {

    /**
     * Log before repository method call.
     * @param joinPoint JoinPoint object - AspectJ JoinPoint object for the method call.
     * @param bean Object - Repository object.
     */
    @Before("repositoryMethods() && target(bean)")
    public void logBeforeRepositoryMethod(JoinPoint joinPoint, Object bean) {
        Advised advised = (Advised) bean;

        String repositoryClassName = advised.getProxiedInterfaces()[0].getTypeName();

        Logger logger = LoggerFactory.getLogger(repositoryClassName);
        String methodName = joinPoint.getSignature().getName();
        String username = MDC.get("context");

        logger.info("[%s] %s(..) method called".formatted(username, methodName));
        if (logger.isDebugEnabled()) {
            logger.debug("[%s] %s(..) method arguments : %s".formatted(username, methodName, Arrays.toString(joinPoint.getArgs())));
        }
    }
}
