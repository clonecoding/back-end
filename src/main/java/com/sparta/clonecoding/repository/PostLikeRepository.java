package com.sparta.clonecoding.repository;

import com.sparta.clonecoding.domain.Post;
import com.sparta.clonecoding.domain.PostLike;
import com.sparta.clonecoding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Boolean existsByUserAndPost(User user, Post post);

    PostLike findByUserAndPost(User user, Post post);

    Optional<PostLike> findByUser(User user);
}
