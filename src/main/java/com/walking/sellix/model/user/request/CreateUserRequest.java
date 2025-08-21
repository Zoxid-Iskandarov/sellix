package com.walking.sellix.model.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Size(max = 100, message = "Email должен быть не длиннее 100 символов")
    private String username;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    @Pattern(regexp = "^[\\p{L} -]+$", message = "Имя может содержать только буквы, пробелы и дефисы")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов")
    @Pattern(regexp = "^[\\p{L} -]+$", message = "Фамилия может содержать только буквы, пробелы и дефисы")
    private String lastName;

    @NotBlank(message = "Пароль не может быть пустым")
    @Length(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String password;

    private String phoneNumber;

    private MultipartFile avatar;
}
