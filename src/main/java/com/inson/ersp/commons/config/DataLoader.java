package com.inson.ersp.commons.config;

import com.inson.ersp.commons.entity.UserEntity;
import com.inson.ersp.commons.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    public static String ADMIN_PINFL;


    @Override
    public void run(String... args)  {
        Optional<UserEntity> byId = userRepository.findById(2L);
        if (byId.isPresent()) {
            log.info(byId.get().getTbLogin());
            ADMIN_PINFL = String.valueOf(byId.get().getToken());
        } else
           log.error("NOT FOUND USER");
    }
}
