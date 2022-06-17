package com.sparta.clonecoding.service;

import com.sparta.clonecoding.domain.Post;
import com.sparta.clonecoding.domain.User;
import com.sparta.clonecoding.dto.PostRequestDto;
import com.sparta.clonecoding.dto.PostResponseDto;
import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.repository.PostRepository;
import com.sparta.clonecoding.repository.UserRepository;
import com.sparta.clonecoding.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public ResponseDto<Object> getAllPosts() {
        List<PostResponseDto> list = new ArrayList<>();
        for (Post post : postRepository.findAllByOrderByTimestampDesc()) {
            list.add(new PostResponseDto(post));
        }
        return new ResponseDto(true,"성공", list);
    }

    public ResponseDto<Object> createPost(UserDetailsImpl userDetails, PostRequestDto postRequestDto, MultipartFile file) {

        return new ResponseDto<>();
    }

    public ResponseDto<Object> updatePost(Long postid, UserDetailsImpl userDetails, PostRequestDto postRequestDto, MultipartFile file) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new IllegalArgumentException("등록되지 않은 사용자입니다.")
        );
        return new ResponseDto<>();
    }

}
