package com.study.sns.controller;

import com.study.sns.dto.CreateArticleDto;
import com.study.sns.global.response.ResponseService;
import com.study.sns.global.response.SingleResponse;
import com.study.sns.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final ResponseService responseService;

    /**
     * 게시글 생성 API
     */
    @PostMapping
    public SingleResponse<CreateArticleDto.Response> createArticle(
            @RequestBody CreateArticleDto.Request req,
            Authentication authentication
    ) {
        return responseService.getSingleResult(
                "게시글이 성공적으로 작성되었습니다.", null
//                CreateArticleDto.Response.of(
//                        articleService.createArticle(
//                                req.getTitle(),
//                                req.getContent(),
//                                authentication.getName()
//                        )
//                )
        );
    }
}
