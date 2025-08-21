package com.walking.sellix.config.init;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioInitializer implements ApplicationRunner {
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private final String bucket;

    @Override
    public void run(ApplicationArguments args) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs
                    .builder()
                    .bucket(bucket)
                    .build());

            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs
                        .builder()
                        .bucket(bucket)
                        .build());

                log.info("Created minio bucket {}", bucket);
            } else {
                log.info("Minio bucket already exists {}", bucket);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
