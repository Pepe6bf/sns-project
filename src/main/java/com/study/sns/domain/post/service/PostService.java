package com.study.sns.domain.post.service;

import com.study.sns.domain.post.dto.CreatePostServiceDto;
import com.study.sns.domain.post.dto.PostDto;
import com.study.sns.domain.post.dto.UpdatePostDto;
import com.study.sns.domain.post.model.entity.Post;
import com.study.sns.domain.post.repository.PostRepository;
import com.study.sns.domain.user.model.entity.User;
import com.study.sns.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    /**
     * 게시글을 생성하는 비즈니스 로직
     */
    @Transactional
    public PostDto createPost(
            CreatePostServiceDto dto
    ) {

        // 현재 로그인 중인 사용자 엔티티 로드
        User currentUser = userService.getCurrentUser();

        // article save
        Post savedPost = postRepository.save(
                Post.of(
                        dto.getTitle(),
                        dto.getContent(),
                        currentUser
                )
        );

        return PostDto.fromEntity(savedPost);
    }

    /**
     * 게시글을 수정하는 비즈니스 로직
     */
    @Transactional
    public void updatePost(
            UpdatePostDto.Request req
    ) {

        User user = userService.getCurrentUser();

        // article exist

        // article permission

    }
}