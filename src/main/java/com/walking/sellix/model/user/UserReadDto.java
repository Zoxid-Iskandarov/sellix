package com.walking.sellix.model.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReadDto {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String avatar;
}
