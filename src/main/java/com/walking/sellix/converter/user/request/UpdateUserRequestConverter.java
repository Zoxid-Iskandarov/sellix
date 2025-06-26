package com.walking.sellix.converter.user.request;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.user.request.UpdateUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class UpdateUserRequestConverter extends AbstractConverter<UpdateUserRequest, User> {
    @Override
    public User convert(UpdateUserRequest source, User target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhoneNumber(source.getPhoneNumber());

        Optional.ofNullable(source.getAvatar())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(avatar -> target.setAvatar(avatar.getOriginalFilename()));

        return target;
    }
}
