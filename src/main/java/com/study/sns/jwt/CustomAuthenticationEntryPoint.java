package com.study.sns.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.sns.global.exception.AccountErrorCode;
import com.study.sns.global.response.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(AccountErrorCode.INVALID_TOKEN.getStatus().value());
        response.getWriter().write(
                ErrorResponse.of(AccountErrorCode.INVALID_TOKEN)
                        .convertJson()
        );
    }
}
