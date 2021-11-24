package com.yp.twitterclonebackend.jwt;

import com.yp.twitterclonebackend.service.CustomUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private final String accessTokenSecret;
    private final String refreshTokenSecret;
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliSeconds;

    private Key accessTokenKey;
    private Key refreshTokenKey;

    public TokenProvider(
            @Value("${jwt.accessTokenSecret}") String accessTokenSecret,
            @Value("${jwt.refreshTokenSecret}") String refreshTokenSecret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.refresh-validity-in-seconds}") long refreshTokenValidityInSeconds
    ) {
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliSeconds = refreshTokenValidityInSeconds * 1000;
    }

    @PostConstruct
    public void init() {
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessTokenSecret);
        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshTokenSecret);
        this.accessTokenKey = Keys.hmacShaKeyFor(accessKeyBytes);
        this.refreshTokenKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        CustomUser principal = (CustomUser) authentication.getPrincipal();
        log.info("{}", principal);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("email", authentication.getName())
                .claim("user_id", principal.getUserId())
                .claim("name", principal.getDisplayName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(accessTokenKey)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        Date validity = new Date(new Date().getTime() + this.refreshTokenValidityInMilliSeconds);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .signWith(refreshTokenKey)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(accessTokenKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<? extends SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        CustomUser principal = new CustomUser(claims.getSubject(), "", authorities, claims.get("user_id", Long.class), claims.get("name", String.class));

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String getUserEmail(String token, TokenType type) {
        Key key = selectKeyByTokenType(type);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token, TokenType type) {
        Key key = selectKeyByTokenType(type);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Key selectKeyByTokenType(TokenType type) {
        Key key;
        if (type == TokenType.ACCESS_TOKEN) {
            key = accessTokenKey;
        } else {
            key = refreshTokenKey;
        }
        return key;
    }
}
