package com.study.sns.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreatePostServiceDto {

    private final String title;
    private final String content;
}
