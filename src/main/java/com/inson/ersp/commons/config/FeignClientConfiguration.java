package com.inson.ersp.commons.config;

import com.inson.ersp.commons.exceptions.ForbiddenException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class FeignClientConfiguration implements RequestInterceptor {
    private static final Pattern BEARER_TOKEN_HEADER_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern BASIC_TOKEN_HEADER_PATTERN = Pattern.compile("^Basic (?<token>[a-zA-Z0-9+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final String authorization = HttpHeaders.AUTHORIZATION;
        final String transactionId = "transactionId";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            String authorizationHeader = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            Matcher jwtMatcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
            Matcher basicMatcher = BASIC_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
            if(jwtMatcher.matches()) {
                requestTemplate.header(authorization, authorizationHeader);
            } else if (basicMatcher.matches()) {
                requestTemplate.header(authorization, authorizationHeader);
            } else {
                throw new ForbiddenException("Not enough credentials!");
            }
            requestTemplate.header(transactionId, requestAttributes.getRequest().getAttribute(transactionId).toString());
        }
    }
}
