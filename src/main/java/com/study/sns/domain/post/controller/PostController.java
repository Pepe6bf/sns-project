package com.study.sns.domain.post.controller;

import com.study.sns.domain.post.dto.CreatePostRequest;
import com.study.sns.domain.post.dto.CreatePostResponse;
import com.study.sns.domain.post.service.PostService;
import com.study.sns.global.response.ResponseService;
import com.study.sns.global.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {

    private final PostService postService;
    private final ResponseService responseService;

    /**
     * 게시글 생성 API
     */
    @PostMapping
    public SingleResponse<CreatePostResponse> createPost(
            @RequestBody CreatePostRequest req
    ) {
        return responseService.getSingleResult(
                "게시글이 성공적으로 작성되었습니다.",
                CreatePostResponse.of(
                        postService.createPost(
                                req.toServiceDto()
                        )
                )
        );
    }
}
