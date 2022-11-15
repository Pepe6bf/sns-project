package com.study.sns.model.dto;

import com.study.sns.model.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserDto {

    @Getter
    @AllArgsConstructor
    public static class Request {

        private String email;

        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long userId;

        public static Response fromEntity(User user) {
            return new Response(user.getId());
        }
    }
}
