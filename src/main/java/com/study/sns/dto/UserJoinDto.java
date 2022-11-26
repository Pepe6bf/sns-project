package com.study.sns.dto;

import com.study.sns.model.constant.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJoinDto {
    @Getter
    @AllArgsConstructor
    public static class Request {

        private String email;

        private String password;
    }

    @Getter
    public static class Response {
        private final Long userId;
        private final String email;
        private UserRole role;

        private Response(
                Long userId,
                String email,
                UserRole role
        ) {
            this.userId = userId;
            this.email = email;
            this.role = role;
        }

        public static Response of(UserDto userDto) {
            return new Response(
                    userDto.getId(),
                    userDto.getEmail(),
                    userDto.getRole()
            );
        }
    }
}
