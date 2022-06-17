package com.sparta.clonecoding.repository;

import com.sparta.clonecoding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository {
    Optional<User> findByUsername(String username);

    Optional<User> findBynickname(String nickname);
}
