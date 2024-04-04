package com.vitisvision.vitisvisionservice.logging;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class RepositoryLoggingAspect {

    @Before("execution(* com.vitisvision.vitisvisionservice.*.*Repository.*(..)) && target(bean)")
    public void logBeforeRepositoryMethod(JoinPoint joinPoint, Object bean) {
        Advised advised = (Advised) bean;

        String repositoryClassName = advised.getProxiedInterfaces()[0].getTypeName();

        Logger logger = LoggerFactory.getLogger(repositoryClassName);
        String methodName = joinPoint.getSignature().getName();
        String username = MDC.get("context");

        logger.info("[%s] %s(..) method called".formatted(username, methodName));
        logger.debug("[%s] %s(..) method arguments : %s".formatted(username, methodName, Arrays.toString(joinPoint.getArgs())));
    }
}
