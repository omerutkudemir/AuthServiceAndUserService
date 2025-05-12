package com.poslifayproject.poslifay.repository;

import com.poslifayproject.poslifay.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Users findById(UUID userId);
}
