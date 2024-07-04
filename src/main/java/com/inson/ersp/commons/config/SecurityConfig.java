package com.inson.ersp.commons.config;


import com.inson.ersp.commons.exceptions.ExceptionHandle;
import com.inson.ersp.commons.payload.enums.StatusMessage;
import com.inson.ersp.commons.payload.response.ApiResponse;
import com.inson.ersp.commons.payload.response.StatusResponse;
import com.inson.ersp.commons.security.AuthorizationFilter;
import com.inson.ersp.commons.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //biz rolini ishlatishimiz uchun kerak bo'
@RequiredArgsConstructor
public class SecurityConfig {
    private final Md5Encoder md5Encoder;
    private final AuthService authService;
    private final AuthorizationFilter authorizationFilter;
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(ExceptionHandle.class);


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] path = {"/swagger-ui/**", "/v3/api-docs/**", "/api/v1/auth/**", "/api/v1/file/**", "/api/v1/policy/file/**"};
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(path)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            System.out.println(LocalDate.now());
                            objectMapper.writeValue(response.getWriter(),
                                    new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of(authException.getMessage()))));
                            logger.error("Unauthorized request: Message: {}, URL: {}, Method: {}, Client: {}, Transaction ID: {}",
                                    authException.getMessage(),
                                    request.getRequestURL(),
                                    request.getMethod(),
                                    request.getRemoteUser() + " " + request.getRemoteAddr(),
                                    request.getAttribute("transactionId"));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            objectMapper.writeValue(response.getWriter(),
                                    new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of(accessDeniedException.getMessage()))));
                            logger.error("Forbidden request: Message: {}, URL: {}, Method: {}, Client: {}, Transaction ID: {}",
                                    accessDeniedException.getMessage(),
                                    request.getRequestURL(),
                                    request.getMethod(),
                                    request.getRemoteUser() + " " + request.getRemoteAddr(),
                                    request.getAttribute("transactionId"));
                        })
                )
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(authService);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
        authProvider.setPasswordEncoder(md5Encoder);
        return authProvider;
    }
}
