package com.walking.sellix.model.announcement;

import com.walking.sellix.entity.AnnouncementImage;
import com.walking.sellix.model.user.UserReadDto;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class AnnouncementReadDto {
    Long id;

    String title;

    String description;

    BigDecimal price;

    String city;

    String createdAt;

    List<AnnouncementImage> images;

    UserReadDto salesman;
}
