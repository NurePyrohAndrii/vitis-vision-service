package com.vitisvision.vitisvisionservice.common.config;

import com.vitisvision.vitisvisionservice.common.entity.auditing.ApplicationAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for auditing in the application.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfig {

    /**
     * <p>Bean for the auditor aware implementation.</p> This bean is used to set the auditor for the entities that are audited.
     *
     * @return the auditor aware implementation
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditorAware();
    }
}
