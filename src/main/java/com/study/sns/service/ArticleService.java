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
    public void createArticle(
            String title,
            String content,
            String email
    ) {
        // user find
        User user = userService.loadUserByEmail(email);

        // article save
        Article savedArticle = articleRepository.save(
                Article.of(
                        title,
                        content,
                        user
                )
        );

//        return ArticleDto.fromEntity(savedArticle);
    }
}
