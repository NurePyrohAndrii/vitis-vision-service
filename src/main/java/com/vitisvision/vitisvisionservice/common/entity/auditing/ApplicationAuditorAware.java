package com.vitisvision.vitisvisionservice.common.entity.auditing;

import com.vitisvision.vitisvisionservice.user.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Auditor aware class for auditing the application entities
 */
public class ApplicationAuditorAware implements AuditorAware<String> {

    private static final ThreadLocal<String> auditor = new ThreadLocal<>();

    public static void setAuditor(String username) {
        auditor.set(username);
    }

    public static void clearAuditor() {
        auditor.remove();
    }

    /**
     * Get the current auditor that is making the changes to the entities
     *
     * @return Optional object of the auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        if (auditor.get() != null) {
            return Optional.of(auditor.get());
        }

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        User principal = (User) authentication.getPrincipal();
        return Optional.ofNullable(principal.getUsername());
    }
}
