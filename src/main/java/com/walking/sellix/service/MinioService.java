package com.walking.sellix.service;

import com.walking.sellix.util.StoragePathGenerator;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private final String bucket;

    @SneakyThrows
    public String upload(MultipartFile file, String username) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String objectName = StoragePathGenerator.generateObjectName(file, username);

        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(bucket)
                    .object(objectName)
                    .stream(stream, file.getSize(), -1)
                    .contentType(file.getContentType() == null ?
                            "application/octet-stream" : file.getContentType())
                    .build());
        }

        return objectName;
    }

    @SneakyThrows
    public void upload(String objectName, InputStream inputStream, String contentType) {
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucket)
                .object(objectName)
                .stream(inputStream, inputStream.available(), -1)
                .contentType(contentType)
                .build());
    }

    @SneakyThrows
    public Optional<byte[]> get(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            return Optional.empty();
        }

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .build())) {
            return Optional.of(stream.readAllBytes());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    public void remove(String objectName) {
        if (!StringUtils.hasText(objectName)) return;

        minioClient.removeObject(RemoveObjectArgs
                .builder()
                .bucket(bucket)
                .object(objectName)
                .build());
    }
}
