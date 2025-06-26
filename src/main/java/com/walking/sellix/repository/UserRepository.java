package com.walking.sellix.repository;

import com.walking.sellix.entity.Role;
import com.walking.sellix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    boolean existsByRole(Role role);


    boolean existsByUsername(String username);
}
