package com.yp.twitterclonebackend.dto;

import com.yp.twitterclonebackend.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TweetResponseDto {
    private Long id;
    private String text;
    private String attachment;
    private Long createdBy;

    public TweetResponseDto(Tweet tweet) {
        this.id = tweet.getTweetId();
        this.text = tweet.getText();
        this.attachment = tweet.getAttachment();
        this.createdBy = tweet.getUser().getUserId();
    }
}
