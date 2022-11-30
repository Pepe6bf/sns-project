package com.study.sns.jwt.filter;

import com.study.sns.jwt.UserPrincipal;
import com.study.sns.jwt.JwtService;
import com.study.sns.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter
        extends OncePerRequestFilter {

    private final String key;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // header parsing
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header. header is null or invalid!");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            // 토큰 유효성 검증
            if (jwtService.tokenIsExpired(token, key)) {
                log.error("Key is expired");
                filterChain.doFilter(request, response);
                return;
            }

            // 토큰으로 부터 이메일 정보 파싱
            String email = jwtService.getEmail(token, key);

            // 사용자 이메일 검증 및 user principal 로드
            UserPrincipal userPrincipal = UserPrincipal.create(
                    userService.loadUserByEmail(email)
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userPrincipal,
                    null,
                    userPrincipal.getAuthorities()
            );
            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            log.error("Error occurs while validation. {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
