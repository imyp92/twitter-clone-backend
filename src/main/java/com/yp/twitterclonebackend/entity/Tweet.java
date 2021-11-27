package com.yp.twitterclonebackend.entity;

import com.yp.twitterclonebackend.dto.TweetDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tweet extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tweet_id")
    private Long id;

    @NotEmpty
    @Size(max = 120)
    private String text;

    private String attachmentUrl;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Tweet(String text, String attachmentUrl, User user) {
        this.text = text;
        this.attachmentUrl = attachmentUrl;
        this.user = user;
    }
}
