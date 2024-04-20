package com.vitisvision.vitisvisionservice.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.framework.Advised;

import java.util.Arrays;

/**
 * Base aspect class to define common pointcuts for logging aspects
 */
@Aspect
public class BaseLoggingAspect {

    /**
     * Pointcut for all user and token repositories methods
     */
    @Pointcut("execution(* com.vitisvision.vitisvisionservice.*.*.*Repository.*(..))")
    public void userAndTokenRepositoriesMethods() {
    }

    /**
     * Pointcut for all domain repositories
     */
    @Pointcut("execution(* com.vitisvision.vitisvisionservice.domain.*.repository.*Repository.*(..))")
    public void domainRepositories() {
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
     * Pointcut for mdc cleanup filter methods
     */
    @Pointcut("execution(* com.vitisvision.vitisvisionservice.common.logging.MdcCleanupFilter.*(..))")
    public void mdcFilterMethods() {
    }

    /**
     * Pointcut for all exception handler methods
     */
    @Pointcut("execution(* com.vitisvision.vitisvisionservice.*.*.*ExceptionHandler.*(..))")
    public void exceptionHandlerMethods() {
    }

    /**
     * Pointcut for all controller advisor methods
     */
    @Pointcut("execution(* (@org.springframework.web.bind.annotation.ControllerAdvice *).*(..))")
    public void advisorMethods() {
    }

    /**
     * Logs method execution, arguments, and return value or exception thrown
     *
     * @param pjp  ProceedingJoinPoint object representing the method call being intercepted
     * @param bean Pass bean object if the target object is a proxy
     * @return Object result of the method call
     * @throws Throwable if an error occurs during method call execution or logging operation
     */
    protected Object logMethodExecution(ProceedingJoinPoint pjp, Object bean) throws Throwable {
        String className;

        // If the target object is a proxy, get the actual class name
        if (bean == null) {
            className = pjp.getTarget().getClass().getName();
        } else {
            Advised advised = (Advised) bean;
            className = advised.getProxiedInterfaces()[0].getTypeName();
        }

        Logger logger = LoggerFactory.getLogger(className);
        String methodName = pjp.getSignature().getName();

        String username = MDC.get("context");

        // If the user uses the application without logging in, set the username as "auth0|anonymous"
        if (username == null) {
            username = "auth0|anonymous";
        }

        logger.info("[%s] %s(..) method called".formatted(username, methodName));

        // If debug level logging is enabled, log method arguments and return value
        boolean isDebugEnabled = logger.isDebugEnabled();
        if (isDebugEnabled) {
            logger.debug("[%s] %s(..) method arguments : %s".formatted(username, methodName, Arrays.toString(pjp.getArgs())));
        }

        Object result;

        // Proceed with the method call and log the return value or exception thrown
        try {
            result = pjp.proceed();

            if (isDebugEnabled) {
                logger.debug("[%s] %s(..) method returned : %s".formatted(username, methodName, result));
            }

            return result;
        } catch (Throwable e) {
            logger.error("[%s] %s(..) method threw an exception : %s".formatted(username, methodName, e));
            throw e;
        }
    }
}
