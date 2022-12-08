package com.study.sns.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.sns.domain.post.dto.CreatePostRequest;
import com.study.sns.domain.post.dto.CreatePostServiceDto;
import com.study.sns.domain.post.dto.PostDto;
import com.study.sns.domain.post.dto.UpdatePostDto;
import com.study.sns.domain.post.exception.PostErrorCode;
import com.study.sns.domain.user.model.entity.User;
import com.study.sns.global.exception.SnsApplicationException;
import com.study.sns.domain.post.service.PostService;
import com.study.sns.global.util.fixture.PostFixture;
import com.study.sns.global.util.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser(value = "tester@email.com")
    @DisplayName("게시글 작성 성공 테스트")
    void 게시글작성_성공() throws Exception {
        // Given
        CreatePostRequest req = new CreatePostRequest(
                "Test Title",
                "Test Content"
        );
        CreatePostServiceDto createPostServiceDto = req.toServiceDto();
        User user = UserFixture.get(
                "tester@email.com",
                "testerPw1234!"
        );
        given(postService.createPost(any(CreatePostServiceDto.class)))
                .willReturn(PostDto.fromEntity(
                        PostFixture.get(
                                user,
                                createPostServiceDto.getTitle(),
                                createPostServiceDto.getContent()
                        )
                ));

        // When
        mockMvc.perform(post("/api/v1/post")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(req)))
                // Then
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("게시글 작성 실패 테스트 - 로그인 하지 않은 사용자")
    void 게시글작성_실패_로그인하지않은_사용자() throws Exception {
        // Given
        CreatePostRequest req = new CreatePostRequest(
                "Test Title",
                "Test Content"
        );
        CreatePostServiceDto createPostServiceDto = req.toServiceDto();
        given(postService.createPost(any(CreatePostServiceDto.class)))
                .willReturn(PostDto.fromEntity(
                        PostFixture.get(
                                null,
                                createPostServiceDto.getTitle(),
                                createPostServiceDto.getContent()
                        )
                ));

        // When
        mockMvc.perform(post("/api/v1/post")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(req))
                )
                // Then
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
//
//    @Test
//    @WithMockUser(value = "tester@email.com")
//    @DisplayName("게시글 수정 성공 테스트")
//    void 게시글수정_성공() throws Exception {
//        // Given
//        String title = "test title";
//        String content = "test content";
//
//        // When
//        // Then
//        mockMvc.perform(patch("/api/v1/post/1")
//                        .contentType(APPLICATION_JSON)
//                        .content(
//                                objectMapper.writeValueAsBytes(
//                                        new UpdatePostDto.Request(title, content)
//                                )
//                        )
//                ).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithAnonymousUser
//    @DisplayName("게시글 수정 실패 테스트 - 로그인 하지 않은 사용자")
//    void 게시글수정_실패_로그인하지않은_사용자() throws Exception {
//        // Given
//        String title = "test title";
//        String content = "test content";
//
//        // When
//        // Then
//        mockMvc.perform(patch("/api/v1/post/1")
//                        .contentType(APPLICATION_JSON)
//                        .content(
//                                objectMapper.writeValueAsBytes(
//                                        new UpdatePostDto.Request(title, content)
//                                )
//                        )
//                ).andDo(print())
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    @WithMockUser(value = "tester@email.com")
//    @DisplayName("게시글 수정 실패 테스트 - 본인 소유가 아닌 게시물 수정 시도")
//    void 게시글수정_실패_본인소유가아닌_게시물수정() throws Exception {
//        // Given
//        String title = "test title";
//        String content = "test content";
//        Long postId = 1L;
//
//        doThrow(new SnsApplicationException(PostErrorCode.INVALID_PERMISSION))
//                .when(postService).updatePost(title, content, postId);
//
//        // When
//        // Then
//        mockMvc.perform(patch("/api/v1/post/1")
//                        .contentType(APPLICATION_JSON)
//                        .content(
//                                objectMapper.writeValueAsBytes(
//                                        new UpdatePostDto.Request(title, content)
//                                )
//                        )
//                ).andDo(print())
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    @WithMockUser(value = "tester@email.com")
//    @DisplayName("게시글 수정 실패 테스트 - 존재하지 않는 게시물 수정 시도")
//    void 게시글수정_실패_존재하지않는_게시물수정() throws Exception {
//        // Given
//        String title = "test title";
//        String content = "test content";
//        Long postId = 1L;
//
//        // mocking
//        doThrow(new SnsApplicationException(PostErrorCode.INVALID_PERMISSION))
//                .when(postService).updatePost(title, content, postId);
//
//        // When
//        // Then
//        mockMvc.perform(patch("/api/v1/post/1")
//                        .contentType(APPLICATION_JSON)
//                        .content(
//                                objectMapper.writeValueAsBytes(
//                                        new UpdatePostDto.Request(title, content)
//                                )
//                        )
//                ).andDo(print())
//                .andExpect(status().isNotFound());
//    }
}
