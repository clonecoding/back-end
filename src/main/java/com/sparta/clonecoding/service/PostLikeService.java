package com.sparta.clonecoding.service;

import com.sparta.clonecoding.domain.Post;
import com.sparta.clonecoding.domain.PostLike;
import com.sparta.clonecoding.domain.User;
import com.sparta.clonecoding.repository.PostLikeRepository;
import com.sparta.clonecoding.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    @Transactional
    public void PostLike(User user, Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(null);

        PostLike existLike = postLikeRepository.findByUserAndPost(user,post);
        if (postLikeRepository.existsByUserAndPost(user,post)){
            postLikeRepository.deleteById(existLike.getPostLikeId());
            postRepository.downLikeCnt(postid);

        }
        else{
            PostLike postLike = PostLike.builder()
                    .post(post)
                    .user(user)
                    .build();
            postLikeRepository.save(postLike);
            postRepository.upLikeCnt(postid);

        }
        postRepository.save(post);
    }
}
