package com.inson.ersp.commons.service;

import com.inson.ersp.commons.entity.UserEntity;
import com.inson.ersp.commons.exceptions.ForbiddenException;
import com.inson.ersp.commons.payload.request.LoginDTO;
import com.inson.ersp.commons.payload.response.LoginResponse;
import com.inson.ersp.commons.repository.UserRepository;
import com.inson.ersp.commons.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.inson.ersp.commons.payload.enums.ResponseEnum.*;


@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws ForbiddenException {
        return userRepository.findByTbLogin(login).orElseThrow(() -> new ForbiddenException("User not found username:" + login));
    }

    public LoginResponse loginUser(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getPassword()));
        UserEntity user = (UserEntity) authenticate.getPrincipal();

        if (user.getToken() == null || !user.getToken().startsWith(BEARER.getText()) || jwtProvider.getUsernameFromToken(user.getToken().substring(7)) == null) {
            String token = jwtProvider.generateToken(user.getTbLogin());
            user.setToken(token);
            userRepository.save(user);
        }
        return new LoginResponse(user.getToken());
    }
}
