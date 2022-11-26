package com.study.sns.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonResponse {

    private final Boolean isSuccess;

    private final Integer status;

    private final String message;
}
