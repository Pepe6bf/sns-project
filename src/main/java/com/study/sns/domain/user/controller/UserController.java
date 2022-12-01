package com.study.sns.domain.user.controller;

import com.study.sns.domain.user.dto.LocalLoginRequest;
import com.study.sns.domain.user.dto.LocalLoginResponse;
import com.study.sns.domain.user.dto.UserJoinRequest;
import com.study.sns.domain.user.dto.UserJoinResponse;
import com.study.sns.global.response.ResponseService;
import com.study.sns.global.response.SingleResponse;
import com.study.sns.domain.user.service.UserService;
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
    public SingleResponse<UserJoinResponse> join(
            @RequestBody UserJoinRequest req
    ) {
        return responseService.getSingleResult(
                "성공적으로 수행되었습니다.",
                UserJoinResponse.of(userService.join(req.toServiceDto()))
        );
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public SingleResponse<LocalLoginResponse> login(
            @RequestBody LocalLoginRequest req
    ) {
        return responseService.getSingleResult(
                "성공적으로 수행되었습니다.",
                LocalLoginResponse.of(userService.login(req.toServiceDto()))
        );
    }
}
