package com.sparta.clonecoding.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
<<<<<<< HEAD
import org.springframework.data.domain.Slice;

import java.util.List;
=======

import java.util.List;

>>>>>>> 9d400f0ee6161b327cd5f7caf980165e4c5d56ee
@NoArgsConstructor
@Setter@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean response;
    private String message;
<<<<<<< HEAD

    private List<T> postList;

=======
    private List<T> postList;

    private String token;

    public ResponseDto(boolean response, String message, String token) {
        this.response = response;
        this.message = message;
        this.token = token;
    }

>>>>>>> 9d400f0ee6161b327cd5f7caf980165e4c5d56ee
    public ResponseDto(boolean response, String message) {
        this.response = response;
        this.message = message;
    }

<<<<<<< HEAD

=======
    public ResponseDto(boolean response, String message, List<T> postList) {
        this.response = response;
        this.message = message;
        this.postList = postList;
    }
>>>>>>> 9d400f0ee6161b327cd5f7caf980165e4c5d56ee
}