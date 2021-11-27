package com.yp.twitterclonebackend.controller;

import com.yp.twitterclonebackend.dto.SignupResponseDto;
import com.yp.twitterclonebackend.dto.UserInfoUpdateDto;
import com.yp.twitterclonebackend.dto.UserDto;
import com.yp.twitterclonebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(new SignupResponseDto(userService.signup(userDto)));
    }

    @PatchMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ADMIN')")
    public ResponseEntity<String> updateDisplayName(@PathVariable Long userId, @RequestBody UserInfoUpdateDto dto) {
        userService.updateUsername(userId, dto.getDisplayName());
        return ResponseEntity.ok(dto.getDisplayName());
    }

//    @GetMapping("/users")
//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ADMIN')")
//    public ResponseEntity<User> getMyUserInfo() {
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
//    }
//
//    @GetMapping("/users/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
//    }


}
