package com.study.sns.global.config.security;

import com.study.sns.jwt.CustomAuthenticationEntryPoint;
import com.study.sns.jwt.filter.JwtTokenFilter;
import com.study.sns.jwt.JwtService;
import com.study.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret-key}")
    private String key;

    private final JwtService jwtService;
    private final UserService userService;

    /**
     * HTTP 보안 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF & Form Login 기능 비활성화
        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();

        // 세션을 사용하지 않기 때문에, 세션 설정을 STATELESS 로 실행
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 권한별 접근 요청 설정
        http.authorizeRequests()
                .antMatchers("/api/*/user/join", "/api/*/user/login").permitAll()
                .antMatchers("/api/**").authenticated();

        // JWT Filter 설정
        http.addFilterBefore(
                        // req 에 포함된 토큰을 확인 후 사용자 정보를 추출
                        new JwtTokenFilter(
                                key,
                                jwtService,
                                userService
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }

    /**
     * Password Encoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
