package com.sparta.clonecoding.service;

import com.sparta.clonecoding.domain.User;
import com.sparta.clonecoding.dto.LoginRequestDto;
import com.sparta.clonecoding.dto.ResponseDto;
import com.sparta.clonecoding.dto.SignUpRequestDto;
import com.sparta.clonecoding.repository.UserRepository;
import com.sparta.clonecoding.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    //닉네임 중복체크
    @Transactional
    public ResponseDto<Object> nicknameCheck(String nickname) {
        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            return new ResponseDto<>(false,"닉네임 중복입니다");
        } else {
            return new ResponseDto<>(true,"성공");
        }
    }

    //username 중복체크
    @Transactional
    public ResponseDto<Object> usernameCheck(String username) {
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return new ResponseDto<>(false,"ID 중복입니다");
        } else {
            return new ResponseDto<>(true,"성공");
        }
    }
    @Transactional
    public ResponseDto<Object> userRegister(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password2 = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();

// 회원가입시 에러메세지들 출력완료
        String Message = "";

        if (password2.length() < 4) {
            Message = "비밀번호 길이가 짧습니다.";
            return new ResponseDto<>(false,Message);
        }
        else if (password2.contains(username)) {
            Message = "비밀번호에 ID를 포함할 수 없습니다.";
            return new ResponseDto<>(false,Message);
        }
        else if (!passwordCheck.equals(password2)) {
            Message = "비밀번호가 일치하지 않습니다.";
            return new ResponseDto<>(false,Message);
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        User user2 = new User(username, password, nickname);
        userRepository.save(user2);
        return new ResponseDto<>(true,"회원가입성공");
    }


    public ResponseDto<Object> loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElse(null);
        if (user != null) {
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                return new ResponseDto<>(false,"아이디 비밀번호를 확인하여주세요");
            }
        }
        jwtTokenProvider.createToken(loginRequestDto.getUsername());
        return new ResponseDto<>(true,"로그인 성공");
    }
}
