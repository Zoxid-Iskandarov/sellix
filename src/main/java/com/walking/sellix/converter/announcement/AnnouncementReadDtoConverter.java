package com.walking.sellix.converter.announcement;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.converter.user.UserReadDtoConverter;
import com.walking.sellix.entity.Announcement;
import com.walking.sellix.model.announcement.AnnouncementReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AnnouncementReadDtoConverter extends AbstractConverter<Announcement, AnnouncementReadDto> {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMMM yyyy'г'", Locale.forLanguageTag("ru"));

    private final UserReadDtoConverter userReadDtoConverter;

    @Override
    public AnnouncementReadDto convert(Announcement source) {
        return new AnnouncementReadDto(
                source.getId(),
                source.getTitle(),
                source.getDescription(),
                source.getPrice(),
                source.getCity(),
                source.getCreatedAt().format(FORMATTER),
                source.getImages(),
                userReadDtoConverter.convert(source.getUser()));
    }
}
