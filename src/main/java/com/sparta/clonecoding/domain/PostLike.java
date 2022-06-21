package com.sparta.clonecoding.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostLike {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    Long postLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private  Post post;


    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
