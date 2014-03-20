package net.physalis.akita;

import net.physalis.akita.presentation.requestid.RequestIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

public class MdcFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdcFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            MDC.clear();
            MDC.put("requestId", RequestIdGenerator.generate());
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
    }
}
