package com.walking.sellix.model.announcement;

import com.walking.sellix.entity.AnnouncementImage;
import com.walking.sellix.model.user.UserReadDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementReadDto {
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String city;

    private String createdAt;

    private List<AnnouncementImage> images;

    private UserReadDto salesman;
}
