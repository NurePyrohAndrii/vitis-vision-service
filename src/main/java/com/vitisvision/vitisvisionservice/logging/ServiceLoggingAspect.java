package com.vitisvision.vitisvisionservice.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceLoggingAspect extends BaseLoggingAspect {

    @Around("serviceMethods()")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        String username = MDC.get("context");

        logger.info("[%s] %s(..) method called".formatted(username, methodName));
        logger.debug("[%s] %s(..) method arguments : %s".formatted(username, methodName, Arrays.toString(joinPoint.getArgs())));

        Object result = joinPoint.proceed();

        logger.debug("[%s] %s(..) method returned : %s".formatted(username, methodName, result));
        return result;
    }

}
