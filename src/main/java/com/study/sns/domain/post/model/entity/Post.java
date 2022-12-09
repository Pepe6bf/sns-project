package com.study.sns.domain.post.model.entity;

import com.study.sns.global.config.audit.BaseEntity;
import com.study.sns.domain.post.model.constant.PostStatus;
import com.study.sns.domain.user.model.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "\"post\"",
        indexes = {
                @Index(columnList = "title"),
                @Index(columnList = "content"),
                @Index(columnList = "createdAt")
        }
)
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private PostStatus status = PostStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    private Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post of(
            String title,
            String content,
            User user
    ) {
        return new Post(title, content, user);
    }
}
