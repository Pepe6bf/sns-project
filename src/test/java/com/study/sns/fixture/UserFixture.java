package com.study.sns.fixture;

import com.study.sns.model.User;

public class UserFixture {

    public static User get(String email, String password) {
        User result = User.builder()
                .id(1L)
                .email("tester@email.com")
                .password("testerPw1234!")
                .build();

        return result;
    }
}
