package com.study.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.sns.dto.CreateArticleDto;
import com.study.sns.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService articleService;

    @Test
    @WithMockUser(value = "tester@email.com")
    @DisplayName("게시글 작성 성공 테스트")
    void 게시글작성_성공() throws Exception {
        // Given
        String title = "test title";
        String content = "test content";

        // When
        // Then
        mockMvc.perform(post("/api/v1/article")
                        .contentType(APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsBytes(
                                        new CreateArticleDto.Request(title, content)
                                )
                        )
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("게시글 작성 실패 테스트 - 로그인 하지 않은 사용자")
    void 게시글작성_실패_로그인하지않은_사용자() throws Exception {
        // Given
        String title = "test title";
        String content = "test content";

        // When
        // Then

        // 로그인 하지 않은 경우
        mockMvc.perform(post("/api/v1/article")
                        .contentType(APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsBytes(
                                        new CreateArticleDto.Request(title, content)
                                )
                        )
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
