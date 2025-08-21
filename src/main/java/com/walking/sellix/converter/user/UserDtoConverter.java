package com.walking.sellix.converter.user;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.user.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter extends AbstractConverter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .id(source.getId())
                .username(source.getUsername())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .role(source.getRole())
                .status(source.isStatus())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .build();
    }
}
