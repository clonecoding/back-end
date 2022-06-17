package com.sparta.clonecoding.controller;

import com.sparta.clonecoding.dto.LoginRequestDto;
import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public ResponseDto<Object> login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.loginUser(loginRequestDto);

    }
}
