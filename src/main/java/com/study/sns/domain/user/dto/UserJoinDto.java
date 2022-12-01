package com.study.sns.domain.user.dto;

import com.study.sns.domain.user.model.constant.UserRole;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJoinDto {
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
        private final Long userId;
        private final String email;
        private final UserRole role;

        public static Response of(UserDto userDto) {
            return new Response(
                    userDto.getId(),
                    userDto.getEmail(),
                    userDto.getRole()
            );
        }
    }
}
