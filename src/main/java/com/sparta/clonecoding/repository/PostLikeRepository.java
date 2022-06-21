package com.sparta.clonecoding.repository;

import com.sparta.clonecoding.domain.Post;
import com.sparta.clonecoding.domain.PostLike;
import com.sparta.clonecoding.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Boolean existsByUserAndPost(User user, Post post);

    List<PostLike> findAllByUser(User user);
    PostLike findByUserAndPost(User user, Post post);
}
