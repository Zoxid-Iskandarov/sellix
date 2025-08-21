package com.walking.sellix.repository;

import com.walking.sellix.entity.Announcement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>,
        JpaSpecificationExecutor<Announcement> {

    @EntityGraph(attributePaths = {"images"})
    Optional<Announcement> findWithImagesById(Long id);

    List<Announcement> findAllByUserId(Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);
}
