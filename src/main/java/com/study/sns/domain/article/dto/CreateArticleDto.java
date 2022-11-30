package com.study.sns.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private final Long articleId;
        private final LocalDateTime createdAt;
        public static Response of(ArticleDto articleDto) {
            return new Response(
                    articleDto.getId(),
                    articleDto.getCreatedAt()
            );
        }
    }
}
