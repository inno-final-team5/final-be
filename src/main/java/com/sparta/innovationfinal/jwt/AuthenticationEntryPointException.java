package com.sparta.innovationfinal.jwt;

import io.jsonwebtoken.io.IOException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointException implements AuthenticationEntryPoint {

    // 401 error
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new objectMapper().writeValueAsString("로그인이 필요합니다."));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
