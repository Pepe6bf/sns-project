package com.study.sns.controller;

import com.study.sns.controller.request.UserJoinRequestDto;
import com.study.sns.model.UserJoinResponseDto;
import com.study.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // TODO : implement
    @PostMapping("/join")
    public void join(@RequestBody UserJoinRequestDto req) {
        // join
        userService.join(req.getEmail(), req.getPassword());
    }

    @PostMapping("/login")
    public void login(@RequestBody UserJoinResponseDto res) {
        userService.login("", "");
    }
}
