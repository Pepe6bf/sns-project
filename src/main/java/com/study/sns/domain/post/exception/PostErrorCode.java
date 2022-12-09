package com.study.sns.domain.post.exception;

import com.study.sns.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND(NOT_FOUND, "PS-001", "Post not founded"),
    INVALID_PERMISSION(UNAUTHORIZED, "PS-002", "Permission is invalid"),;

    private final HttpStatus status;
    private final String code;
    private final String message;
}