package com.walking.sellix.model.announcement.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class UpdateAnnouncementRequest {
    private Long id;

    @NotBlank(message = "Название объявления не может быть пустым")
    @Size(min = 5, max = 100, message = "Название должно быть от 5 до 100 символов")
    private String title;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 10, max = 10000, message = "Описание должно быть от 10 до 10000 символов")
    private String description;

    @NotNull(message = "Цена должна быть указана")
    @DecimalMin(value = "0", message = "Цена не может быть отрицательной")
    @DecimalMax(value = "10000000000", message = "Цена не может превышать 10 000 000 000")
    private BigDecimal price;

    @NotBlank(message = "Город должен быть указан")
    private String city;

    private MultipartFile image1;

    private MultipartFile image2;

    private MultipartFile image3;
}
