package com.walking.sellix.model.user;

import com.walking.sellix.entity.Role;

public record UserFilter(String username,
                         String firstName,
                         String lastName,
                         String phoneNumber,
                         Role role,
                         Boolean status) {
}
