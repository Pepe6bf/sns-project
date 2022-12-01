package com.study.sns.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequest {

    public UserJoinRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;

    public UserJoinServiceDto toServiceDto() {
        return new UserJoinServiceDto(email, password);
    }
}
