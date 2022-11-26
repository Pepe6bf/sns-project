package com.study.sns.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO : develop
@Getter
@RequiredArgsConstructor
public class SnsApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
}
