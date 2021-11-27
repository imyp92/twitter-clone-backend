package com.yp.twitterclonebackend.dto;

import com.yp.twitterclonebackend.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TweetDto {
    private Long id;
    private String text;
    private String attachmentUrl;
    private Long createdBy;
    private LocalDateTime createdAt;

    public TweetDto(Tweet tweet) {
        this.id = tweet.getId();
        this.text = tweet.getText();
        this.attachmentUrl = tweet.getAttachmentUrl();
        this.createdBy = tweet.getUser().getUserId();
        this.createdAt = tweet.getCreatedDate();
    }
}
