package com.study.sns.global.util.fixture;

import com.study.sns.domain.post.model.entity.Post;
import com.study.sns.domain.user.model.entity.User;

public class PostFixture {

    public static Post get(
            User user,
            String title,
            String content
    ) {

        Post result = Post.of(
                title,
                content,
                user
        );

        return result;
    }
}
