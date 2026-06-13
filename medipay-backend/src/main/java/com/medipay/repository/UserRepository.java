package com.medipay.repository;

import com.medipay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByIdAndIsActiveTrue(Long id);
    
    // Admin queries
    List<User> findAllByOrderByCreatedAtDesc();
    Long countByIsActive(Boolean isActive);
    Long countByRolesName(String roleName);
}
