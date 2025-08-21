package com.walking.sellix.repository.specification;

import com.walking.sellix.entity.Announcement;
import com.walking.sellix.model.announcement.AnnouncementFilter;
import com.walking.sellix.entity.Announcement_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementSpecification {
    public static Specification<Announcement> filterAnnouncements(AnnouncementFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.title() != null && !filter.title().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get(Announcement_.title)),
                        "%" + filter.title().toLowerCase() + "%"));
            }

            if (filter.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(Announcement_.price), filter.minPrice()));
            }

            if (filter.maxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get(Announcement_.price), filter.maxPrice()));
            }

            if (filter.city() != null && !filter.city().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get(Announcement_.city)),
                        filter.city().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
