package com.study.sns.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO : develop
@Getter
@RequiredArgsConstructor
public class SnsApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
