package com.walking.sellix.converter.announcement;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.Announcement;
import com.walking.sellix.model.announcement.AnnouncementDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class AnnouncementDtoConverter extends AbstractConverter<Announcement, AnnouncementDto> {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMMM yyyy'г' HH:mm", Locale.forLanguageTag("ru"));

    @Override
    public AnnouncementDto convert(Announcement source) {
        return AnnouncementDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .price(source.getPrice())
                .city(source.getCity())
                .createdAt(source.getCreatedAt().format(FORMATTER))
                .build();
    }
}
