package com.study.sns.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserJoinServiceDto {

    private final String email;
    private final String password;
}
