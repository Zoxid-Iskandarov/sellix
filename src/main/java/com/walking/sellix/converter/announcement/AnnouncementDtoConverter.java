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
        return new AnnouncementDto(
                source.getId(),
                source.getTitle(),
                source.getDescription(),
                source.getPrice(),
                source.getCity(),
                source.getCreatedAt().format(FORMATTER));
    }
}
