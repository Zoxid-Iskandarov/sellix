package com.walking.sellix.converter.user.request;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.user.request.CreateUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class CreateUserRequestConverter extends AbstractConverter<CreateUserRequest, User> {
    @Override
    public User convert(CreateUserRequest source) {
        User target = User.builder()
                .username(source.getUsername())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .password(source.getPassword())
                .phoneNumber(source.getPhoneNumber())
                .build();
        
        Optional.ofNullable(source.getAvatar())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(avatar -> target.setAvatar(avatar.getOriginalFilename()));

        return target;
    }
}
