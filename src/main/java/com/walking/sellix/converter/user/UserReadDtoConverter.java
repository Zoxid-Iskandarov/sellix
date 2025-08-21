package com.walking.sellix.converter.user;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.user.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadDtoConverter extends AbstractConverter<User, UserReadDto> {
    @Override
    public UserReadDto convert(User source) {
        return UserReadDto.builder()
                .id(source.getId())
                .username(source.getUsername())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .avatar(source.getAvatar())
                .build();
    }
}
