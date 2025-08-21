package com.walking.sellix.service.security;

import com.walking.sellix.entity.CustomUserDetails;
import com.walking.sellix.entity.Role;
import com.walking.sellix.repository.AnnouncementRepository;
import com.walking.sellix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccessChecker {
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    public boolean isOwner(Long targetUserId, Long currentUserId) {
        return userRepository.findById(targetUserId)
                .map(user -> user.getId().equals(currentUserId))
                .orElse(false);
    }

    public boolean isOwnerOfAnnouncement(Long announcementId, Long currentUserId) {
        return announcementRepository.findById(announcementId)
                .map(announcement -> announcement.getUser().getId().equals(currentUserId))
                .orElse(false);
    }

    public boolean canDeleteAnnouncement(Long announcementId, CustomUserDetails userDetails) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority()
                        .equals(Role.ADMIN.getAuthority()));

        if (isAdmin) return true;

        return announcementRepository.existsByIdAndUserId(announcementId, userDetails.getId());
    }
}
