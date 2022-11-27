package com.study.sns.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateArticleDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {

        public Request(String title, String content) {
            this.title = title;
            this.content = content;
        }

        private String title;
        private String content;
    }
}
