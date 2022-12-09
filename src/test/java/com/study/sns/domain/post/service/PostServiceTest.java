package com.study.sns.domain.post.service;

import com.study.sns.domain.post.dto.CreatePostServiceDto;
import com.study.sns.domain.post.dto.PostDto;
import com.study.sns.domain.post.dto.UpdatePostServiceDto;
import com.study.sns.domain.post.model.entity.Post;
import com.study.sns.domain.post.repository.PostRepository;
import com.study.sns.domain.user.account.jwt.exception.AccountErrorCode;
import com.study.sns.domain.user.model.entity.User;
import com.study.sns.domain.user.service.UserService;
import com.study.sns.global.exception.SnsApplicationException;
import com.study.sns.global.util.fixture.PostFixture;
import com.study.sns.global.util.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;
    @MockBean
    private PostRepository postRepository;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("게시글 작성 성공 테스트")
    void 게시글작성_성공() throws Exception {
        // Given
        User currentUser = UserFixture.get(
                "tester@email.com",
                "testerPw1234!"
        );
        CreatePostServiceDto dto = new CreatePostServiceDto(
                "Test Title",
                "Test Content"
        );
        given(userService.getCurrentUser())
                .willReturn(currentUser);
        given(postRepository.save(any(Post.class)))
                .willReturn(
                        PostFixture.get(
                                currentUser,
                                dto.getTitle(),
                                dto.getContent()
                        )
                );

        // When
        PostDto savedPost = postService.createPost(dto);

        // Then
        assertThat(savedPost.getTitle()).isEqualTo(dto.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(dto.getContent());
        assertThat(savedPost.getUser()).isEqualTo(currentUser);
    }

    @Test
    @DisplayName("게시글 작성 실패 테스트 - 존재하지 않는 사용자")
    void 게시글작성_실패_존재하지않는_사용자() throws Exception {
        // Given
        CreatePostServiceDto dto = new CreatePostServiceDto(
                "Test Title",
                "Test Content"
        );
        given(userService.getCurrentUser())
                .willThrow(new SnsApplicationException(AccountErrorCode.USER_NOT_FOUND));
        given(postRepository.save(any(Post.class)))
                .willReturn(
                        PostFixture.get(
                                null,
                                dto.getTitle(),
                                dto.getContent()
                        )
                );

        // When
        SnsApplicationException e = catchThrowableOfType(
                () -> {
                    postService.createPost(dto);
                },
                SnsApplicationException.class
        );

        // Then
        assertThat(e.getErrorCode()).isEqualTo(AccountErrorCode.USER_NOT_FOUND);
    }

    @Test
    @DisplayName("게시글 수정 성공 테스트")
    void 게시글수정_성공() throws Exception {
        // Given
        User currentUser = UserFixture.get(
                "tester@email.com",
                "testerPw1234!"
        );
        Long postId = 1L;
        UpdatePostServiceDto dto = new UpdatePostServiceDto(
                "Test Title",
                "Test Content"
        );
        Post savedPost = PostFixture.get(
                currentUser,
                dto.getTitle(),
                dto.getContent()
        );

        given(userService.getCurrentUser())
                .willReturn(mock(User.class));
        given(postRepository.findById(postId))
                .willReturn(Optional.of(savedPost));

        // Then

    }

    @Test
    @DisplayName("게시글 수정 실패 테스트 - 존재하지 않는 게시글 수정 시도")
    void 포스트수정시_포스트가_존재하지않는_경우() throws Exception {
        // Given

        // When

        // Then
    }

    @Test
    @DisplayName("게시글 수정 실패 테스트 - 게시글 수정 권한이 없는 경우")
    void 게시글수정시_권한이_없는_경우() throws Exception {
        // Given

        // When

        // Then
    }
}
