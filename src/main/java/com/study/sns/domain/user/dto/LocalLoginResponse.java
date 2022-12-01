package com.study.sns.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalLoginResponse {

    private final String token;

    public static LocalLoginResponse of(String token) {
        return new LocalLoginResponse(token);
    }
}
