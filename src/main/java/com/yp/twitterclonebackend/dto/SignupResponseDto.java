package com.yp.twitterclonebackend.dto;

import com.yp.twitterclonebackend.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupResponseDto {
    private String email;
    private String username;
    private LocalDateTime createdDate;

    public SignupResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.createdDate = user.getCreatedDate();
    }
}
