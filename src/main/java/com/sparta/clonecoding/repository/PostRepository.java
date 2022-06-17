package com.sparta.clonecoding.repository;

import com.sparta.clonecoding.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
