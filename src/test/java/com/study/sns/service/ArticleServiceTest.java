package com.study.sns.service;

import com.study.sns.global.exception.AccountErrorCode;
import com.study.sns.global.exception.SnsApplicationException;
import com.study.sns.model.entity.Article;
import com.study.sns.model.entity.User;
import com.study.sns.repository.ArticleRepository;
import com.study.sns.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("게시글 작성 성공 테스트")
    void 게시글작성_성공() throws Exception {
        // Given
        String title = "Test Title";
        String content = "Test Content";
        String email = "tester@email.com";

        // When
        // mocking
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(mock(User.class)));
        when(articleRepository.save(any()))
                .thenReturn(mock(Article.class));

        // Then
        Assertions.assertDoesNotThrow(
                () -> articleService.createArticle(
                        title,
                        content,
                        email
                )
        );
    }

    @Test
    @DisplayName("게시글 작성 실패 테스트 - 존재하지 않는 사용자")
    void 게시글작성_실패_존재하지않는_사용자() throws Exception {
        // Given
        String title = "Test Title";
        String content = "Test Content";
        String email = "tester@email.com";

        // When
        // mocking
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());
        when(articleRepository.save(any()))
                .thenReturn(mock(Article.class));

        // Then
        SnsApplicationException e = Assertions.assertThrows(
                SnsApplicationException.class,
                () -> articleService.createArticle(
                        title,
                        content,
                        email
                )
        );

        Assertions.assertEquals(AccountErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }
}
