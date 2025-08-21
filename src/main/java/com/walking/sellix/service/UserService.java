package com.walking.sellix.service;

import com.walking.sellix.converter.user.UserDtoConverter;
import com.walking.sellix.converter.user.UserReadDtoConverter;
import com.walking.sellix.converter.user.request.CreateUserRequestConverter;
import com.walking.sellix.converter.user.request.UpdateUserRequestConverter;
import com.walking.sellix.entity.CustomUserDetails;
import com.walking.sellix.entity.Role;
import com.walking.sellix.entity.User;
import com.walking.sellix.model.user.UserDto;
import com.walking.sellix.model.user.UserFilter;
import com.walking.sellix.model.user.UserReadDto;
import com.walking.sellix.model.user.request.CreateUserRequest;
import com.walking.sellix.model.user.request.UpdateUserRequest;
import com.walking.sellix.repository.UserRepository;
import com.walking.sellix.repository.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final MinioService minioService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserDtoConverter userDtoConverter;
    private final UserReadDtoConverter userReadDtoConverter;
    private final CreateUserRequestConverter createUserRequestConverter;
    private final UpdateUserRequestConverter updateUserRequestConverter;

    public Page<UserDto> getAll(UserFilter filter, Pageable pageable) {
        return userRepository.findAll(UserSpecification.filterUsers(filter), pageable)
                .map(userDtoConverter::convert);
    }

    public Optional<UserReadDto> getById(Long id) {
        return userRepository.findById(id)
                .map(userReadDtoConverter::convert);
    }

    public Optional<byte[]> getAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getAvatar)
                .filter(StringUtils::hasText)
                .flatMap(minioService::get);
    }

    @Transactional
    public UserReadDto create(CreateUserRequest userRequest) {
        return Optional.of(userRequest)
                .map(createUserRequestConverter::convert)
                .map(user -> {
                    uploadAvatar(userRequest.getAvatar(), user);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setStatus(true);
                    user.setRole(Role.USER);

                    return userRepository.save(user);
                })
                .map(userReadDtoConverter::convert)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UpdateUserRequest userRequest) {
        return userRepository.findById(id)
                .map(user -> {
                    removeOldAvatar(userRequest.getAvatar(), user);
                    uploadAvatar(userRequest.getAvatar(), user);

                    return updateUserRequestConverter.convert(userRequest, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadDtoConverter::convert);
    }

    @Transactional
    public void changeStatus(Long id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setStatus(!user.isStatus());

                    userRepository.saveAndFlush(user);
                });
    }

    @Transactional
    public void changeRole(Long id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    Role newRole = user.getRole() == Role.USER ? Role.ADMIN : Role.USER;
                    user.setRole(newRole);

                    userRepository.saveAndFlush(user);
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new CustomUserDetails(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.isStatus(),
                        true,
                        true,
                        true,
                        List.of(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with username %s not found".formatted(username)));
    }

    private void uploadAvatar(MultipartFile avatar, User user) {
        if (avatar != null && !avatar.isEmpty()) {
            String objectName = minioService.upload(avatar, user.getUsername());
            user.setAvatar(objectName);
        }
    }

    private void removeOldAvatar(MultipartFile avatar, User user) {
        if (avatar != null && !avatar.isEmpty() && StringUtils.hasText(user.getAvatar())) {
            minioService.remove(user.getAvatar());
        }
    }
}
