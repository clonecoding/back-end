package com.sparta.clonecoding.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean response;
    private String message;


    private List<T> postList;


    private String token;

    public ResponseDto(boolean response, String message, String token) {
        this.response = response;
        this.message = message;
        this.token = token;
    }


    public ResponseDto(boolean response, String message) {
        this.response = response;
        this.message = message;
    }


    public ResponseDto(boolean response, String message, List<T> postList) {
        this.response = response;
        this.message = message;
        this.postList = postList;
    }

}