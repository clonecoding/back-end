package com.sparta.clonecoding.repository;

import com.sparta.clonecoding.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByTimestampDesc();
    Optional<Post> findById(Long postid);


//    Page<Post> findAllByOrderByPostIdDesc(Pageable pageable);



    @Modifying
    @Query("update Post a set a.postLikes = a.postLikes + 1 where a.id = :id")
    void upLikeCnt(Long id);

    @Modifying
    @Query("update Post a set a.postLikes = a.postLikes - 1 where a.id = :id")
    void downLikeCnt(Long id);

}
