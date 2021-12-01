package com.yp.twitterclonebackend.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tweet extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tweet_id")
    private Long tweetId;

    private String text;

    @Basic(fetch = FetchType.LAZY)
    @Column(length = 10000000)
    private String attachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Tweet(String text, String attachment, User user) {
        this.text = text;
        this.attachment = attachment;
        this.user = user;
    }

    public void updateText(String text) {
        this.text = text;
    }
}
