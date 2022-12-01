package com.study.sns.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalLoginDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {

        public Request(String email, String password) {
            this.email = email;
            this.password = password;
        }

        private String email;

        private String password;
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {

        private final String token;

        public static Response of(String token) {
            return new Response(token);
        }
    }
}
