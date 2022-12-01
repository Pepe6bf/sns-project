package com.study.sns.domain.user.dto;

import com.study.sns.domain.user.model.constant.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJoinResponse {
    private final Long userId;
    private final String email;
    private final UserRole role;

    public static UserJoinResponse of(UserDto userDto) {
        return new UserJoinResponse(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getRole()
        );
    }
}
