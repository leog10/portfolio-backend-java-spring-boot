package com.leonardogilli.security.repository;

import com.leonardogilli.security.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByTokenPassword(String tokenPassword);
    boolean existsByUsername(String username);
    boolean existsByUsernameOrEmail(String username, String email);
    boolean existsByEmail(String email);
}
