package com.walking.sellix.model.announcement;

import java.math.BigDecimal;

public record AnnouncementFilter(String title,
                                 BigDecimal minPrice,
                                 BigDecimal maxPrice,
                                 String city) {
}
