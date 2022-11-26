package com.study.sns.model.dto;

import com.study.sns.model.constant.UserRole;
import com.study.sns.model.constant.UserStatus;
import com.study.sns.model.entity.User;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDto {

    private Long id;

    private String email;

    private UserRole role;

    private UserStatus status;

    private UserDto(
            Long id,
            String email,
            UserRole role,
            UserStatus status
    ) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public static UserDto fromEntity(
            User user
    ) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }
}
