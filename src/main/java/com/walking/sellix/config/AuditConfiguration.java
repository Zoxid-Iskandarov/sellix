package com.walking.sellix.config;

import com.walking.sellix.entity.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(authentication -> authentication.isAuthenticated() &&
                        !"anonymousUser".equals(authentication.getPrincipal()))
                .map(authentication -> {
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof CustomUserDetails userDetails) {
                        return userDetails.getUsername();
                    }
                    return null;
                });
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
