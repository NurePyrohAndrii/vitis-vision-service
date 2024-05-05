package com.vitisvision.vitisvisionservice.common.logging;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filter to clean up MDC after request processing. This filter should be the last filter in the chain.
 */
@Component
@Order
public class MdcCleanupFilter implements Filter {

    /**
     * Remove the context from MDC after request processing.
     *
     * @param request  the request
     * @param response the response
     * @param chain    the filter chain
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("context");
        }
    }
}
