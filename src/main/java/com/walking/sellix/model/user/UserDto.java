package com.walking.sellix.model.user;

import com.walking.sellix.entity.Role;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserDto {
    Long id;

    String username;

    String firstName;

    String lastName;

    String phoneNumber;

    Role role;

    boolean status;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
