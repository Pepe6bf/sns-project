package com.study.sns.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserJoinRequestDto {

    private final String email;
    private final String password;
}
