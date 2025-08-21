package com.walking.sellix.model.announcement;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementDto {
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String city;

    private String createdAt;
}
