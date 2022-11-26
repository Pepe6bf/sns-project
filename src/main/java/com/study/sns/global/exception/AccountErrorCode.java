package com.study.sns.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {

    DUPLICATED_USER_EMAIL(CONFLICT, "ACC-001", "이미 존재하는 이메일입니다."),;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
