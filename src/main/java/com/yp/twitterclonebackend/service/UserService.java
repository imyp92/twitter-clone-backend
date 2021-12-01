package com.yp.twitterclonebackend.service;

import com.yp.twitterclonebackend.dto.UserDto;
import com.yp.twitterclonebackend.entity.Authority;
import com.yp.twitterclonebackend.entity.User;
import com.yp.twitterclonebackend.repository.UserRepository;
import com.yp.twitterclonebackend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public void updateUsername(Long userId, String newUsername) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다.")).changeUsername(newUsername);
        return;
    }
}
