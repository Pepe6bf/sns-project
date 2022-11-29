package com.study.sns.controller;

import com.study.sns.dto.LocalLoginDto;
import com.study.sns.dto.UserJoinDto;
import com.study.sns.global.response.ResponseService;
import com.study.sns.global.response.SingleResponse;
import com.study.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    /**
     * 회원가입 API
     */
    @PostMapping("/join")
    public SingleResponse<UserJoinDto.Response> join(
            @RequestBody UserJoinDto.Request req
    ) {
        // join
        return responseService.getSingleResult(
                "성공적으로 수행되었습니다.",
                UserJoinDto.Response.of(
                        userService.join(req.getEmail(), req.getPassword())
                )
        );
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public SingleResponse<LocalLoginDto.Response> login(
            @RequestBody LocalLoginDto.Request req
    ) {

        return responseService.getSingleResult(
                "성공적으로 수행되었습니다.",
                LocalLoginDto.Response.of(
                        userService.login(req.getEmail(), req.getPassword())
                )
        );
    }
}
