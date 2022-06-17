package com.sparta.clonecoding.controller;

import com.sparta.clonecoding.dto.PostRequestDto;
import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.security.UserDetailsImpl;
import com.sparta.clonecoding.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }


    @GetMapping("/api/post")
    public ResponseDto<Object> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping("/api/post")
    public ResponseDto<Object> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestPart(value = "postDto") PostRequestDto postRequestDto,
                                          @RequestPart(value = "file") MultipartFile file) throws IOException {
        return postService.createPost(userDetails,postRequestDto,file);
    }

    @PutMapping("/api/post/{postid}")
    public ResponseDto<Object> updatePost(@PathVariable Long postid,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestPart(value = "postDto") PostRequestDto postRequestDto,
                                          @RequestPart(value = "file") MultipartFile file){
        return postService.updatePost(postid,userDetails,postRequestDto,file);
    }

    @DeleteMapping("/api/post/{postid}")
    public ResponseDto<Object> deletePost(@PathVariable Long postid,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(postid, userDetails);
    }
}
