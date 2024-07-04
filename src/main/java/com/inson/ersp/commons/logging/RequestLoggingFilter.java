package com.inson.ersp.commons.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

import static com.inson.ersp.ErspApplication.TRANSACTION_ID;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger("req-logs");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpRequest);

        // Generate and set request ID
        TRANSACTION_ID = UUID.randomUUID().toString();
        cachedRequest.setAttribute("request-id", TRANSACTION_ID);
        // Log request ID
        logger.info("Request ID: {}", TRANSACTION_ID);
        // Log request URL
        logger.info("Request URL: {}", cachedRequest.getRequestURL());
        // Log client IP
        String clientIp = cachedRequest.getRemoteAddr();
        logger.info("Client IP: {}", clientIp);

        // Log headers
        Enumeration<String> headerNames = cachedRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = cachedRequest.getHeader(headerName);
            logger.info("Header: {} = {}", headerName, headerValue);
        }

        // Log parameters
        Map<String, String[]> params = cachedRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            logger.info("Parameter: {} = {}", entry.getKey(), String.join(",", entry.getValue()));
        }

        // Log request body
        String body = getRequestBody(cachedRequest);
        logger.info("Body: {}", body);

        chain.doFilter(cachedRequest, response);
    }

    private String getRequestBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            logger.error("Error reading request body", e);
        }
        return stringBuilder.toString();
    }
}