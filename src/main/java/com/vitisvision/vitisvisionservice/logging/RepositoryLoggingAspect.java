package com.vitisvision.vitisvisionservice.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryLoggingAspect extends BaseLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(RepositoryLoggingAspect.class);

    @Before("repositoryMethods()")
    public void logRepositoryMethods() {
        logger.info("Repository method called");
    }
}
