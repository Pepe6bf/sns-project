package com.study.sns.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePostDto {

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
        private final Long postId;
        private final LocalDateTime createdAt;

        public static Response of(PostDto postDto) {
            return new Response(
                    postDto.getPostId(),
                    postDto.getCreatedAt()
            );
        }
    }
}
