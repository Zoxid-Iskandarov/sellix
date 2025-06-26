package com.walking.sellix.model.announcement;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AnnouncementDto {
    Long id;

    String title;

    String description;

    BigDecimal price;

    String city;

    String createdAt;
}
