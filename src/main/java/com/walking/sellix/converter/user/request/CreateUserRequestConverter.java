package com.walking.sellix.converter.user.request;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.user.request.CreateUserRequest;
import org.springframework.stereotype.Component;


@Component
public class CreateUserRequestConverter extends AbstractConverter<CreateUserRequest, User> {
    @Override
    public User convert(CreateUserRequest source) {
        return User.builder()
                .username(source.getUsername())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .password(source.getPassword())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}
