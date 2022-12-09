package com.study.sns.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePostResponse {
    private final Long postId;
    private final LocalDateTime updatedAt;

    public static UpdatePostResponse of(PostDto postDto) {
        return new UpdatePostResponse(
                postDto.getPostId(),
                postDto.getUpdatedAt()
        );
    }
}
