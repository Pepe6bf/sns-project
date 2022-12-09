package com.study.sns.domain.user.account.jwt.exception;

import com.study.sns.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {

    DUPLICATED_USER_EMAIL(CONFLICT, "ACC-001", "Duplicated user email"),
    USER_NOT_FOUND(NOT_FOUND, "ACC-002", "User not found"),
    INVALID_PASSWORD(UNAUTHORIZED, "ACC-003", "Invalid Password"),
    INVALID_TOKEN(UNAUTHORIZED, "ACC-004", "Invalid Token");

    private final HttpStatus status;
    private final String code;
    private final String message;
}