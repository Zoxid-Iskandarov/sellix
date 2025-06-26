package com.walking.sellix.converter.announcement.request;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.Announcement;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.announcement.request.CreateAnnouncementRequest;
import com.walking.sellix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateAnnouncementRequestConverter extends AbstractConverter<CreateAnnouncementRequest, Announcement> {
    private final UserRepository userRepository;

    @Override
    public Announcement convert(CreateAnnouncementRequest source) {
        return Announcement.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .price(source.getPrice())
                .city(source.getCity())
                .user(getUser(source.getUserId()))
                .build();
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElseThrow();
    }
}
