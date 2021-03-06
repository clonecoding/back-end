package com.sparta.clonecoding.service;

import com.sparta.clonecoding.domain.Post;
import com.sparta.clonecoding.domain.PostLike;
import com.sparta.clonecoding.domain.User;
import com.sparta.clonecoding.dto.*;
import com.sparta.clonecoding.repository.PostLikeRepository;
import com.sparta.clonecoding.repository.PostRepository;
import com.sparta.clonecoding.repository.UserRepository;
import com.sparta.clonecoding.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.directory.SearchResult;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostLikeRepository postLikeRepository;

    private final S3Service s3Service;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, PostLikeRepository postLikeRepository, S3Service s3Service) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
        this.s3Service = s3Service;
    }


    public ResponseDto<Object> getAllPosts(UserDetailsImpl userDetails) {
        List<PostResponseDto> list = new ArrayList<>();
        User user = userDetails.getUser();
        LocalDateTime now = LocalDateTime.now();
        for (Post post : postRepository.findAllByOrderByTimestampDesc()) {
            String timestamp = "";
            if(now.getYear() != post.getTimestamp().getYear()){
                timestamp = timestamp + (now.getYear()-post.getTimestamp().getYear()) + "년전";
            }else if(now.getMonthValue() != post.getTimestamp().getMonthValue()){
                timestamp = timestamp + (now.getMonthValue()-post.getTimestamp().getMonthValue()) + "달전";
            }else if(now.getDayOfMonth() != post.getTimestamp().getDayOfMonth()){
                timestamp = timestamp + (now.getDayOfMonth()-post.getTimestamp().getDayOfMonth()) + "일전";
            }else if(now.getHour() != post.getTimestamp().getHour()){
                timestamp = timestamp + (now.getHour()-post.getTimestamp().getHour()) + "시간전";
            }else if(now.getMinute() != post.getTimestamp().getMinute()) {
                timestamp = timestamp + (now.getMinute() - post.getTimestamp().getMinute()) + "분전";
            }else {
                timestamp = "방금전";
            }
            PostResponseDto postResponseDto = new PostResponseDto(post, timestamp);
            if(postLikeRepository.existsByUserAndPost(user,post)){
                postResponseDto.setLikeCheck(true);
            }
            list.add(postResponseDto);
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

    //상세페이지 조회하기

    public ResponseDto<Object> getOnePost(Long postid, UserDetailsImpl userDetails) {

        Optional<Post> post = postRepository.findById(postid);
        if (!post.isPresent()) {
            return new ResponseDto<>(false, "게시물이 존재하지않습니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        String timestamp = "";
        if(now.getYear() != post.get().getTimestamp().getYear()){
            timestamp = timestamp + (now.getYear()-post.get().getTimestamp().getYear()) + "년전";
        }else if(now.getMonthValue() != post.get().getTimestamp().getMonthValue()){
            timestamp = timestamp + (now.getMonthValue()-post.get().getTimestamp().getMonthValue()) + "달전";
        }else if(now.getDayOfMonth() != post.get().getTimestamp().getDayOfMonth()){
            timestamp = timestamp + (now.getDayOfMonth()-post.get().getTimestamp().getDayOfMonth()) + "일전";
        }else if(now.getHour() != post.get().getTimestamp().getHour()){
            timestamp = timestamp + (now.getHour()-post.get().getTimestamp().getHour()) + "시간전";
        }else if(now.getMinute() != post.get().getTimestamp().getMinute()) {
            timestamp = timestamp + (now.getMinute() - post.get().getTimestamp().getMinute()) + "분전";
        }else {
            timestamp = "방금전";
        }

        PostDetailResponseDto postDetail = new PostDetailResponseDto(post.get(), timestamp);
        if(postLikeRepository.existsByUserAndPost(userDetails.getUser(), post.get())){
            postDetail.setLikeCheck(true);
        }

        return new ResponseDto<>(true, "성공", postDetail);
    }
}
