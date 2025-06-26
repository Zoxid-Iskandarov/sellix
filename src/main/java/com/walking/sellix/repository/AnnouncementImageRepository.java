package com.walking.sellix.repository;

import com.walking.sellix.entity.AnnouncementImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnnouncementImageRepository extends JpaRepository<AnnouncementImage, Long> {
    @Query("select ai from AnnouncementImage ai where ai.isPreview = true and ai.announcement.id = :announcementId")
    Optional<AnnouncementImage> getPreviewImage(Long announcementId);
}
