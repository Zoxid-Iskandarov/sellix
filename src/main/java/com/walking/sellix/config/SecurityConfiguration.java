package com.walking.sellix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.walking.sellix.entity.Role.ADMIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login",
                                "/registration",
                                "/announcements/{id:[0-9]+}/preview",
                                "/announcements/{id:[0-9]+}/image",
                                "/users/{id:[0-9]+}/avatar",
                                "/announcements/user/{id:[0-9]+}")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/announcements", "/announcements/{id:[0-9]+}")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/users")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**")
                        .hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/announcements"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
