package com.sparta.clonecoding.dto;

import com.sparta.clonecoding.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;

    private String title;

    private int price;

    private String image;

    private String category;

    private LocalDateTime timestamp;

    private int postLikes;

    private  boolean likeCheck;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.image = post.getImage();
        this.category = post.getCategory();
        this.timestamp = post.getTimestamp();
        this.postLikes = post.getPostLikes();
    }
}
