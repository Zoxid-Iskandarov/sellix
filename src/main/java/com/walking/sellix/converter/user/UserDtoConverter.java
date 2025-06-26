package com.walking.sellix.converter.user;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.user.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter extends AbstractConverter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return new UserDto(
                source.getId(),
                source.getUsername(),
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber(),
                source.getRole(),
                source.isStatus(),
                source.getCreatedAt(),
                source.getUpdatedAt());
    }
}
