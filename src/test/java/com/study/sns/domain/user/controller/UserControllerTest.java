package com.study.sns.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.sns.domain.user.account.jwt.exception.AccountErrorCode;
import com.study.sns.domain.user.dto.UserDto;
import com.study.sns.domain.user.dto.UserJoinRequest;
import com.study.sns.domain.user.dto.UserJoinServiceDto;
import com.study.sns.domain.user.model.entity.User;
import com.study.sns.domain.user.service.UserService;
import com.study.sns.global.exception.SnsApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void 회원가입() throws Exception {
        // Given
        UserJoinRequest req = new UserJoinRequest(
                "tester@email.com",
                "testerPw1234!"
        );
        UserJoinServiceDto userJoinServiceDto = req.toServiceDto();
        given(userService.join(any(UserJoinServiceDto.class)))
                .willReturn(UserDto.fromEntity(
                        User.of(
                                userJoinServiceDto.getEmail(),
                                passwordEncoder.encode(userJoinServiceDto.getPassword())
                        )));

        // When
        mockMvc.perform(post("/api/v1/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(req)))
                // Then
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 이미 가입된 이메일로 회원 가입을 시도하는 경우")
    void 회원가입실패_존재하는_이메일() throws Exception {
        // Given
        UserJoinRequest req = new UserJoinRequest(
                "tester@email.com",
                "testerPw1234!"
        );
        UserJoinServiceDto userJoinServiceDto = req.toServiceDto();
        given(userService.join(any(UserJoinServiceDto.class)))
                .willThrow(
                        new SnsApplicationException(AccountErrorCode.DUPLICATED_USER_EMAIL));

        // When
        mockMvc.perform(post("/api/v1/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(req)))
                // Then
                .andDo(print())
                .andExpect(status().isConflict());
    }

//    @Test
//    @DisplayName("로그인 성공 테스트")
//    void 로그인() throws Exception {
//        // Given
//        LocalLoginDto.Request req = new LocalLoginDto.Request(
//                "tester@email.com",
//                "testPw1234!"
//        );
//
//        // When
//        when(userService.login(req)).thenReturn("test_token");
//
//        // Then
//        mockMvc.perform(post("/api/v1/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(req))
//                ).andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 존재하지 않는 email로 로그인 하는 경우")
//    void 로그인실패_존재하지않는_회원() throws Exception {
//        // Given
//        LocalLoginDto.Request req = new LocalLoginDto.Request(
//                "tester@email.com",
//                "testPw1234!"
//        );
//
//        // When
//        when(userService.login(req)).thenThrow(
//                new SnsApplicationException(AccountErrorCode.USER_NOT_FOUND)
//        );
//
//        // Then
//        mockMvc.perform(post("/api/v1/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(req))
//                ).andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 일치하지 않는 비밀번호로 로그인 하는 경우")
//    void 로그인실패_일치하지않는_패스워드() throws Exception {
//        // Given
//        LocalLoginDto.Request req = new LocalLoginDto.Request(
//                "tester@email.com",
//                "testPw1234!"
//        );
//
//        // When
//        when(userService.login(req)).thenThrow(
//                new SnsApplicationException(AccountErrorCode.INVALID_PASSWORD)
//        );
//
//        // Then
//        mockMvc.perform(post("/api/v1/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(req))
//                ).andDo(print())
//                .andExpect(status().isUnauthorized());
//    }
}
