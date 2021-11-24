package com.yp.twitterclonebackend.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
public class UserAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_authority_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_name")
    private Authority authority;
}
