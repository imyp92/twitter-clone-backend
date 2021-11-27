package com.yp.twitterclonebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionUser {
    private Long userId;
    private String email;
    private String username;
}
