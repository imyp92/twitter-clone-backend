package com.yp.twitterclonebackend.controller;

import com.yp.twitterclonebackend.dto.LoginRequestDto;
import com.yp.twitterclonebackend.dto.LoginResponseDto;
import com.yp.twitterclonebackend.dto.SignupResponseDto;
import com.yp.twitterclonebackend.dto.UserDto;
import com.yp.twitterclonebackend.jwt.TokenProvider;
import com.yp.twitterclonebackend.service.CustomUser;
import com.yp.twitterclonebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDto> authorize(@Valid @RequestBody LoginRequestDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        CustomUser principal = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(new LoginResponseDto(principal.getUserId(), authentication.getName(), principal.getDisplayName(), accessToken, refreshToken), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(new SignupResponseDto(userService.signup(userDto)));
    }
}
