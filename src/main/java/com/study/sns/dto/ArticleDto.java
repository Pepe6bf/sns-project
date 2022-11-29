package com.study.sns.dto;

import com.study.sns.model.entity.Article;
import com.study.sns.model.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDto {
    private final Long id;
    private final String title;
    private final String content;
    private final User user;
    private final LocalDateTime createdAt;

    private ArticleDto(
            Long id,
            String title,
            String content,
            User user,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public static ArticleDto fromEntity(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getUser(),
                article.getCreatedAt()
        );
    }
}
