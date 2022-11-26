package com.study.sns.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {

    DUPLICATED_USER_EMAIL(CONFLICT, "ACC-001", "이미 존재하는 이메일입니다."),
    USER_NOT_FOUND(NOT_FOUND, "ACC-002", "존재하지 않는 사용자 입니다."),
    INVALID_PASSWORD(UNAUTHORIZED, "ACC-003", "패스워드가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}