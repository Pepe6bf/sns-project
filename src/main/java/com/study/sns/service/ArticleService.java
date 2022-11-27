package com.study.sns.service;

import com.study.sns.model.entity.Article;
import com.study.sns.model.entity.User;
import com.study.sns.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Transactional
    public void createArticle(
            String title,
            String content,
            String email
    ) {
        // user find
        User user = userService.findUser(email);

        // article save
        Article savedArticle = articleRepository.save(
                Article.of(
                        title,
                        content,
                        user
                )
        );

        // return
    }
}
