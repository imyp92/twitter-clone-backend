package com.yp.twitterclonebackend.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApplicationJsonRequestSizeLimitFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        if ((method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put") || method.equalsIgnoreCase("patch")) &&
                isApplicationJson(request) && request.getContentLengthLong() > 10000000) {
            throw new IOException("Request content exceeded limit of 10MB");
        }
        filterChain.doFilter(request, response);
    }

    private boolean isApplicationJson(HttpServletRequest httpRequest) {
        return (MediaType.APPLICATION_JSON.isCompatibleWith(MediaType
                .parseMediaType(httpRequest.getHeader(HttpHeaders.CONTENT_TYPE))));
    }
}
