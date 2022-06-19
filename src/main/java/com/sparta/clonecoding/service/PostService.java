package com.sparta.clonecoding.service;

import com.sparta.clonecoding.domain.Post;
import com.sparta.clonecoding.domain.User;
import com.sparta.clonecoding.dto.FileRequestDto;
import com.sparta.clonecoding.dto.PostRequestDto;
import com.sparta.clonecoding.dto.PostResponseDto;
import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.repository.PostRepository;
import com.sparta.clonecoding.repository.UserRepository;
import com.sparta.clonecoding.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, S3Service s3Service) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }


    public ResponseDto<Object> getAllPosts() {
        List<PostResponseDto> list = new ArrayList<>();
        for (Post post : postRepository.findAllByOrderByTimestampDesc()) {
            list.add(new PostResponseDto(post));
        }
        return new ResponseDto(true,"성공", list);
    }

    @Transactional
    public ResponseDto<Object> createPost(UserDetailsImpl userDetails, PostRequestDto postRequestDto, MultipartFile file){
        FileRequestDto fileRequestDto = s3Service.upload(file);
        Post post = new Post(postRequestDto, fileRequestDto, userDetails.getUser());
        postRepository.save(post);
        return new ResponseDto<>(true, "저장성공");
    }

    @Transactional
    public ResponseDto<Object> updatePost(Long postid, UserDetailsImpl userDetails, PostRequestDto postRequestDto, MultipartFile file) {
        Optional<User> user = userRepository.findById(userDetails.getUser().getId());
        if(!user.isPresent()) {
           return new ResponseDto<>(false,"등록되지 않은 사용자입니다.");
        }
        Optional<Post> post = postRepository.findById(postid);
        if(!post.isPresent()) {
           return new ResponseDto<>(false, "게시글이 존재하지 않습니다.");
        }
        s3Service.deleteImageUrl(post.get().getFileName());
        FileRequestDto fileRequestDto = s3Service.upload(file);
        post.get().update(postRequestDto, fileRequestDto, userDetails.getUser());
        postRepository.save(post.get());
        return new ResponseDto<>(true,"수정완료");
    }


    public ResponseDto<Object> deletePost(Long postid, UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findById(userDetails.getUser().getId());
        if(!user.isPresent()) {
            return new ResponseDto<>(false,"등록되지 않은 사용자입니다.");
        }
        Optional<Post> post = postRepository.findById(postid);
        if(!post.isPresent()) {
            return new ResponseDto<>(false, "게시글이 존재하지 않습니다.");
        }
        s3Service.deleteImageUrl(post.get().getFileName());
        postRepository.deleteById(postid);
        return new ResponseDto<>(true,"삭제완료");

    }

// 상세페이지 조회하기
    public ResponseDto<Object> getOnePost(Long postid) {

        Optional<Post> post = postRepository.findById(postid);
        if (!post.isPresent()){
            return  new ResponseDto<>(false, "게시물이 존재하지않습니다.");
        }
        PostResponseDto postResponseDto = new PostResponseDto(post.get());
        return  new ResponseDto<>(true,"성공",postResponseDto);
    }
}
