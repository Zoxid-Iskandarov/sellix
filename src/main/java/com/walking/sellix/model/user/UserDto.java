package com.walking.sellix.model.user;

import com.walking.sellix.entity.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Role role;

    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
