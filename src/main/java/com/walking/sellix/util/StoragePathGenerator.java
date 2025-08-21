package com.walking.sellix.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UtilityClass
public class StoragePathGenerator {
    private static String generateFileName(MultipartFile file) {
        return "%s.%s".formatted(UUID.randomUUID(), getFileExtension(file.getOriginalFilename()));
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "jpg";
        }

        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public static String generateObjectName(MultipartFile file, String username) {
        return "users/%s/%s".formatted(username, generateFileName(file));
    }

    public static String generateObjectName(MultipartFile file, Long id) {
        return "announcements/%d/%s".formatted(id, generateFileName(file));
    }
}
