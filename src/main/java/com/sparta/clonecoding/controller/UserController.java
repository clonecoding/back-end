package com.sparta.clonecoding.controller;

import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.dto.SignUpRequestDto;
import com.sparta.clonecoding.repository.UserRepository;
import com.sparta.clonecoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private  final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }



    @PostMapping("/user/signup")
    public ResponseDto userRegister(@RequestBody SignUpRequestDto signUpRequestDto){
       return userService.userRegister(signUpRequestDto);
    }

    //닉네임 중복확인
    @GetMapping("/user/nickname/{nickname}")
    public ResponseDto nicknameCheck(@PathVariable String nickname){
        return userService.nicknameCheck(nickname);

    }
    //아이디 중복확인
    @GetMapping("/user/username/{username}")
    public ResponseDto usernameCheck(@PathVariable String username){
        return userService.usernameCheck(username);
    }
}
