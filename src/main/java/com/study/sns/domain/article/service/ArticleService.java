package com.study.sns.service;

import com.study.sns.dto.ArticleDto;
import com.study.sns.model.entity.Article;
import com.study.sns.model.entity.User;
import com.study.sns.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    /**
     * 게시글을 생성하는 비즈니스 로직
     */
    @Transactional
    public ArticleDto createArticle(
            String title,
            String content
    ) {

        // 현재 로그인 중인 사용자 엔티티 로드
        User user = userService.getCurrentUser();

        // article save
        Article savedArticle = articleRepository.save(
                Article.of(
                        title,
                        content,
                        user
                )
        );

        return ArticleDto.fromEntity(savedArticle);
    }

    /**
     * 게시글을 수정하는 비즈니스 로직
     */
    @Transactional
    public void updateArticle(
            String title,
            String content,
            Long postId
    ) {

        User user = userService.getCurrentUser();

        // article exist

        // article permission

    }
}
