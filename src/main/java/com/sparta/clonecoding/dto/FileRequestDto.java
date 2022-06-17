package com.sparta.clonecoding.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileRequestDto {

    private String imageUrl;
    private String fileName;

}