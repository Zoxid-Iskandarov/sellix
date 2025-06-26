package com.walking.sellix.validator;

import com.walking.sellix.model.user.request.CreateUserRequest;
import com.walking.sellix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateUserRequest request = (CreateUserRequest) target;

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return;
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            errors.rejectValue(
                    "username",
                    "409",
                    "Пользователь с таким email уже существует"
            );
        }
    }
}
