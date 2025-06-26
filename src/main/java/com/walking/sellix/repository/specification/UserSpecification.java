package com.walking.sellix.repository.specification;

import com.walking.sellix.entity.User;
import com.walking.sellix.entity.User_;
import com.walking.sellix.model.user.UserFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UserSpecification {
    public static Specification<User> filterUsers(UserFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.username() != null && !filter.username().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get(User_.username)),
                        filter.username().toLowerCase()));
            }

            if (filter.firstName() != null && !filter.firstName().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get(User_.firstName)),
                        filter.firstName().toLowerCase()));
            }

            if (filter.lastName() != null && !filter.lastName().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get(User_.lastName)),
                        filter.lastName().toLowerCase()));
            }

            if (filter.phoneNumber() != null && !filter.phoneNumber().isBlank()) {
                predicates.add(cb.equal(root.get(User_.phoneNumber),
                        filter.phoneNumber()));
            }

            if (filter.role() != null) {
                predicates.add(cb.equal(root.get(User_.role), filter.role()));
            }

            if (filter.status() != null) {
                predicates.add(cb.equal(root.get(User_.status), filter.status()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
