package com.inson.ersp.commons.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger("req-logs");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest httpRequest) || !(response instanceof HttpServletResponse httpResponse)) {
            chain.doFilter(request, response);
            return;
        }

        // Retrieve the request ID
        String requestId = (String) httpRequest.getAttribute("request-id");

        com.inson.ersp.commons.logging.CachedBodyHttpServletResponse cachedResponse = new com.inson.ersp.commons.logging.CachedBodyHttpServletResponse((HttpServletResponse) response);

        chain.doFilter(request, cachedResponse);

        // Log response ID
        logger.info("Request ID: {}", requestId);

        // Log response status
        int status = cachedResponse.getStatus();
        logger.info("Response Status: {}", status);

        // Log headers
        for (String headerName : cachedResponse.getHeaderNames()) {
            String headerValue = cachedResponse.getHeader(headerName);
            logger.info("Response Header: {} = {}", headerName, headerValue);
        }

        // Log response body
        byte[] body = cachedResponse.getBody();
        String responseBody = new String(body, cachedResponse.getCharacterEncoding());
        logger.info("Response Body: {}", responseBody);

        // Write the cached body to the original response
        httpResponse.getOutputStream().write(body);
    }
}