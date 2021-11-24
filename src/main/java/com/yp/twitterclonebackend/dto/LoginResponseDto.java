package com.yp.twitterclonebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDto {

    private Long uid;
    private String email;
    private String displayName;
    private String accessToken;
    private String refreshToken;
}
