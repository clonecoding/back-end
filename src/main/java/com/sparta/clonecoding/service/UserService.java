package com.sparta.clonecoding.service;

import com.sparta.clonecoding.domain.User;
import com.sparta.clonecoding.dto.LoginRequestDto;
import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.repository.UserRepository;
import com.sparta.clonecoding.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Autowired
    public UserService(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseDto<Object> loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElse(null);
        if (user != null) {
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                return new ResponseDto<>(false,"아이디 비밀번호를 확인하여주세요");
            }
        }
        String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
        return new ResponseDto<>(true,"로그인 성공",token);
    }
}
