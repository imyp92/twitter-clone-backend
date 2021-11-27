package com.yp.twitterclonebackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority extends BaseTimeEntity {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}