package com.study.sns.fixture;

import com.study.sns.model.entity.User;

public class UserFixture {

    public static User get(String email, String password) {
        User result = User.of(
                email,
                password
        );

        return result;
    }
}
