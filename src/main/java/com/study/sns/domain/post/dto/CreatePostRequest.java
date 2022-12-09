package com.study.sns.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostRequest {

    private String title;
    private String content;

    public CreatePostRequest(
            String title,
            String content
    ) {
        this.title = title;
        this.content = content;
    }

    public CreatePostServiceDto toServiceDto() {
        return new CreatePostServiceDto(title, content);
    }
}
