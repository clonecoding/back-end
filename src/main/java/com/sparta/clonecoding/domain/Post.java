package com.sparta.clonecoding.domain;

import com.sparta.clonecoding.dto.FileRequestDto;
import com.sparta.clonecoding.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;


    @Column(nullable = false,columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(PostRequestDto postRequestDto, FileRequestDto fileRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.price = postRequestDto.getPrice();
        this.image = fileRequestDto.getImage();
        this.fileName = fileRequestDto.getFileName();
        this.category = postRequestDto.getCategory();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto, FileRequestDto fileRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.price = postRequestDto.getPrice();
        this.image = fileRequestDto.getImage();
        this.fileName = fileRequestDto.getFileName();
        this.category = postRequestDto.getCategory();
        this.user = user;

    }
}
