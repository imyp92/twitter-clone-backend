package com.yp.twitterclonebackend.jwt;

import com.yp.twitterclonebackend.enumeration.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt, TokenType.ACCESS_TOKEN)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다. url: {}", authentication.getName(), requestURI);
        } else {
            log.debug("유효한 액세스 토큰이 없습니다. url: {}", requestURI);
            String refreshToken = resolveToken(httpServletRequest);
            log.debug("refresh token={}", refreshToken);
            if (refreshToken != null) {
                if (tokenProvider.validateToken(refreshToken, TokenType.REFRESH_TOKEN)) {
                    String email = tokenProvider.getUserEmail(refreshToken, TokenType.REFRESH_TOKEN);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    String newToken = tokenProvider.createAccessToken(authentication);
                    log.debug(newToken);

                    httpServletResponse.addHeader(AUTHORIZATION_HEADER, "Bearer " + newToken);
                } else {
                    log.debug("유효한 리프레시 토큰이 없습니다.");
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
//
//    private String resolveRefreshToken(HttpServletRequest request) {
//        String refreshToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Refresh ")) {
//            return refreshToken.substring(8);
//        }
//        return null;
//    }
}
