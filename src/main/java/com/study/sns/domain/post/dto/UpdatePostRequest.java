package com.study.sns.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePostRequest {

    private String title;
    private String content;

    public UpdatePostRequest(
            String title,
            String content
    ) {
        this.title = title;
        this.content = content;
    }

    public UpdatePostServiceDto toServiceDto() {
        return new UpdatePostServiceDto(title, content);
    }
}
