package com.yp.twitterclonebackend.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {
    private final Long userId;
    private String displayName;
    public CustomUser(String email, String password, Collection<? extends GrantedAuthority> authorities, Long userId, String displayName) {
        super(email, password, authorities);
        this.userId = userId;
        this.displayName = displayName;
    }
}
