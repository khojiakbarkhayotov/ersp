package com.inson.ersp.commons.security;



import com.inson.ersp.commons.config.Md5Encoder;
import com.inson.ersp.commons.payload.enums.StatusMessage;
import com.inson.ersp.commons.payload.response.ApiResponse;
import com.inson.ersp.commons.payload.response.StatusResponse;
import com.inson.ersp.commons.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.inson.ersp.commons.payload.enums.ResponseEnum.*;


@Component
public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    // private final Md5Encoder md5Encoder;
    private static final ApiResponse invalidToken = new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of("Invalid token")));
    private static ApiResponse checkUser = new ApiResponse(null);

    public AuthorizationFilter(@Lazy AuthenticationManager authenticationManager, JwtProvider jwtProvider, AuthService authService) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token;
        if (authorizationHeader != null ){
            if (authorizationHeader.startsWith(BEARER.getText())) {
                token = authorizationHeader.substring(7);
                String username = jwtProvider.getUsernameFromToken(token);

                if (username == null) {
                    responseWrite(response, invalidToken);
                    return;
                }

                UserDetails userDetails = authService.loadUserByUsername(username);
                if (check(userDetails)) {
                    responseWrite(response, checkUser);
                    return;
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else if (authorizationHeader.startsWith(BASIC.getText())) {
                super.doFilterInternal(request, response, filterChain);
                /*token = token.substring(6);
                byte[] decodedBytes = Base64.getDecoder().decode(token);
                String credentials = new String(decodedBytes);
                String[] usernameAndPassword = credentials.split(":");
                UserDetails userDetails = authService.loadUserByUsername(usernameAndPassword[0].toUpperCase());
                if (md5Encoder.matches(usernameAndPassword[1].toUpperCase(),userDetails.getPassword())){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, usernameAndPassword, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    responseWrite(response,invalidToken); return;
                }*/
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public boolean check(UserDetails userDetails){
        if (!userDetails.isAccountNonExpired())
            checkUser = new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of("Account Expired")));
        if (!userDetails.isEnabled())
            checkUser = new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of("Account Enabled")));
        if (!userDetails.isCredentialsNonExpired())
            checkUser = new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of("Credentials Expired")));
        if (!userDetails.isAccountNonLocked())
            checkUser = new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of("Account Locked")));
        return checkUser.getStatus() == null;
    }
    public void responseWrite(HttpServletResponse response, ApiResponse apiResponse) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

}
