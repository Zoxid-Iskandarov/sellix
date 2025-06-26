package com.walking.sellix.model.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserRequest {
    Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    @Pattern(regexp = "^[\\p{L} -]+$", message = "Имя может содержать только буквы, пробелы и дефисы")
    String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов")
    @Pattern(regexp = "^[\\p{L} -]+$", message = "Фамилия может содержать только буквы, пробелы и дефисы")
    String lastName;

    String phoneNumber;

    MultipartFile avatar;
}
