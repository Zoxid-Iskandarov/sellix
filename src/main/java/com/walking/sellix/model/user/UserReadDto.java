package com.walking.sellix.model.user;

import lombok.Value;

@Value
public class UserReadDto {
    Long id;

    String username;

    String firstName;

    String lastName;

    String phoneNumber;

    String avatar;
}
