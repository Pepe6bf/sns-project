package com.study.sns.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePostResponse {
    private final Long postId;
    private final LocalDateTime createdAt;

    public static CreatePostResponse of(PostDto postDto) {
        return new CreatePostResponse(
                postDto.getPostId(),
                postDto.getCreatedAt()
        );
    }
}
