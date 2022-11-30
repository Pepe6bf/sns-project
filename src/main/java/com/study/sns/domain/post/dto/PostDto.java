package com.study.sns.domain.post.dto;

import com.study.sns.domain.post.model.entity.Post;
import com.study.sns.domain.user.model.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private final Long postId;
    private final String title;
    private final String content;
    private final User user;
    private final LocalDateTime createdAt;

    private PostDto(
            Long postId,
            String title,
            String content,
            User user,
            LocalDateTime createdAt
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public static PostDto fromEntity(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser(),
                post.getCreatedAt()
        );
    }
}
