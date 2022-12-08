package com.study.sns.global.util.fixture;

import com.study.sns.domain.user.model.entity.User;

public class UserFixture {

    public static User get(
            String email,
            String password
    ) {
        User result = User.of(
                email,
                password
        );

        return result;
    }
}
