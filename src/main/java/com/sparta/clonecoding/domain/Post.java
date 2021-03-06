package com.sparta.clonecoding.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.clonecoding.dto.FileRequestDto;
import com.sparta.clonecoding.dto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String comment;

    @Column
    private Boolean likeCheck=false;

    @Column
    private int postLikes;

    @JsonIgnoreProperties({"post"})
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "post")
    private List<PostLike> likeList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(PostRequestDto postRequestDto, FileRequestDto fileRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.price = postRequestDto.getPrice();
        this.image = fileRequestDto.getImage();
        this.fileName = fileRequestDto.getFileName();
        this.category = postRequestDto.getCategory();
        this.comment = postRequestDto.getComment();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto, FileRequestDto fileRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.price = postRequestDto.getPrice();
        this.image = fileRequestDto.getImage();
        this.fileName = fileRequestDto.getFileName();
        this.category = postRequestDto.getCategory();
        this.comment = postRequestDto.getComment();
        this.user = user;

    }
}
