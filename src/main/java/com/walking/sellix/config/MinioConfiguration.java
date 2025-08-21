package com.walking.sellix.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfiguration {
    @Value("${minio.endpoint}")
    private final String endpoint;
    @Value("${minio.access-key}")
    private final String minioAccessKey;
    @Value("${minio.secret-key}")
    private final String minioSecretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
    }
}
