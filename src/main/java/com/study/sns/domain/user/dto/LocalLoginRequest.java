package com.study.sns.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalLoginRequest {

    public LocalLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;

    public LocalLoginServiceDto toServiceDto() {
        return new LocalLoginServiceDto(email, password);
    }
}
