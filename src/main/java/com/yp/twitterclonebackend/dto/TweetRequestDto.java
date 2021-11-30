package com.yp.twitterclonebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TweetRequestDto {
    private Long id;
    private String text;
    private String attachment;
}
