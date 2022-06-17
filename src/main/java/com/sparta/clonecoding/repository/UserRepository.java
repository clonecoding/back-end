package com.sparta.clonecoding.repository;

import com.sparta.clonecoding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
