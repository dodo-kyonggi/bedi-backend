package com.dodo.bedi.login.repository;

import com.dodo.bedi.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> ,CustomUserRepository{
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
}
