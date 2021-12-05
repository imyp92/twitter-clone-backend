package com.yp.twitterclonebackend.controller;

import com.yp.twitterclonebackend.annotation.LoginUser;
import com.yp.twitterclonebackend.dto.SessionUser;
import com.yp.twitterclonebackend.dto.SignupResponseDto;
import com.yp.twitterclonebackend.dto.UserInfoUpdateDto;
import com.yp.twitterclonebackend.dto.UserDto;
import com.yp.twitterclonebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<String> updateDisplayName(@PathVariable Long userId, @RequestBody UserInfoUpdateDto dto, @LoginUser SessionUser user) {
        userService.updateUsername(userId, dto.getDisplayName());
        return ResponseEntity.ok(dto.getDisplayName());
    }
}
