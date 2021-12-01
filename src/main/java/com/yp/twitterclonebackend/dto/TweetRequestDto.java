package com.yp.twitterclonebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TweetRequestDto {
    private Long id;
    @Size(max = 120)
    private String text;
    private String attachment;
}
