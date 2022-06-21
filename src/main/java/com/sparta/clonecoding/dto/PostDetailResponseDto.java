package com.sparta.clonecoding.dto;

import com.sparta.clonecoding.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class PostDetailResponseDto {

    private Long id;

    private String title;

    private int price;

    private String image;

    private String category;

    private String timestamp;

    private String comment;

    private int postLikes;

    private  boolean likeCheck;

    private String nickname;

    public PostDetailResponseDto(Post post, String timestamp) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.price =post.getPrice() ;
        this.image = post.getImage();
        this.category = post.getCategory();
        this.comment = post.getComment();
        this.timestamp = timestamp;
        this.nickname = post.getUser().getNickname();
    }
}

