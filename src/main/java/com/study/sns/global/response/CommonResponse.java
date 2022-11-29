package com.study.sns.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonResponse {

    private final Boolean isSuccess;

    private final String message;
}
