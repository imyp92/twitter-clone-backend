package com.yp.twitterclonebackend.config.auth;

import com.yp.twitterclonebackend.annotation.LoginUser;
import com.yp.twitterclonebackend.dto.LoginResponseDto;
import com.yp.twitterclonebackend.dto.SessionUser;
import com.yp.twitterclonebackend.service.CustomUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginUserAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean isSessionUserClass = LoginResponseDto.class.equals(parameter.getParameterType());
        return hasLoginUserAnnotation && isSessionUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new SessionUser(principal.getUserId(), principal.getUsername(), principal.getDisplayName());
    }
}
