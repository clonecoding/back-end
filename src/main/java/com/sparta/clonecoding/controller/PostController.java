package com.sparta.clonecoding.controller;

import com.sparta.clonecoding.domain.User;
import com.sparta.clonecoding.dto.PostRequestDto;
import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.security.UserDetailsImpl;
import com.sparta.clonecoding.service.PostLikeService;
import com.sparta.clonecoding.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PostController {

    private final PostService postService;

    private  final PostLikeService postLikeService;

    @Autowired
    public PostController(PostService postService, PostLikeService postLikeService) {
        this.postService = postService;
        this.postLikeService = postLikeService;
    }


    @GetMapping("/api/post")
    public ResponseDto<Object> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping(value = "/api/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<Object> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestPart(value = "postDto") PostRequestDto postRequestDto,
                                          @RequestPart(value = "file") MultipartFile file) throws IOException {
        return postService.createPost(userDetails, postRequestDto, file);
    }

    @PutMapping("/api/post/{postid}")
    public ResponseDto<Object> updatePost(@PathVariable Long postid,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestPart(value = "postDto") PostRequestDto postRequestDto,
                                          @RequestPart(value = "file") MultipartFile file) {
        return postService.updatePost(postid, userDetails, postRequestDto, file);
    }


    @DeleteMapping("/api/post/{postid}")
    public ResponseDto<Object> deletePost(@PathVariable Long postid,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(postid, userDetails);
    }

    @GetMapping("/api/post/{postid}")
    public ResponseDto<Object> getOnePost(@PathVariable Long postid) {
        return postService.getOnePost(postid);
    }

    @PostMapping("/api/post/{postid}")
    public void PostLike(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postLikeService.PostLike(userDetails.getUser(),postid);
    }
}