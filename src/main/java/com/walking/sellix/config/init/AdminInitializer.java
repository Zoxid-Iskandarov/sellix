package com.walking.sellix.config.init;

import com.walking.sellix.entity.Role;
import com.walking.sellix.entity.User;
import com.walking.sellix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.existsByRole(Role.ADMIN)) {
            User admin = User.builder()
                    .username("admin@gmail.com")
                    .firstName("Admin")
                    .lastName("Admin")
                    .password(passwordEncoder.encode("DefaultAdmin"))
                    .role(Role.ADMIN)
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);

            log.info("Default Admin has been created");
        } else {
            log.info("Default Admin already exists");
        }
    }
}
