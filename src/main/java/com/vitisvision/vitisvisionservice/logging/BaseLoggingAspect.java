package com.vitisvision.vitisvisionservice.logging;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BaseLoggingAspect {

    @Pointcut("@target(org.springframework.data.repository.NoRepositoryBean)")
    public void repositoryMethods() {
    }

    @Pointcut("@target(org.springframework.stereotype.Service)")
    public void serviceMethods() {
    }

}
