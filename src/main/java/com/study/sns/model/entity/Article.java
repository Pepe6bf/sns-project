package com.study.sns.model.entity;

import com.study.sns.global.config.audit.BaseEntity;
import com.study.sns.model.constant.ArticleStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "\"article\"",
        indexes = {
                @Index(columnList = "title"),
                @Index(columnList = "content"),
                @Index(columnList = "createdAt")
        }
)
@Entity
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private ArticleStatus status = ArticleStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    private Article(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Article of(
            String title,
            String content,
            User user
    ) {
        return new Article(title, content, user);
    }
}
