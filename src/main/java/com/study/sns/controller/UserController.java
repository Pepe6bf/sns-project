package com.study.sns.controller;

import com.study.sns.global.response.ResponseService;
import com.study.sns.global.response.SingleResponse;
import com.study.sns.dto.UserJoinDto;
import com.study.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    // TODO : implement
    @PostMapping("/join")
    public SingleResponse<UserJoinDto.Response> join(
            @RequestBody UserJoinDto.Request req
    ) {
        // join
        return responseService.getSingleResult(
                OK.value(),
                "성공적으로 수행되었습니다.",
                UserJoinDto.Response.of(
                        userService.join(req.getEmail(), req.getPassword())
                )
        );
    }

//    @PostMapping("/login")
//    public void login(
//            @RequestBody UserJoinResponseDto res
//    ) {
//        userService.login("", "");
//    }
}
