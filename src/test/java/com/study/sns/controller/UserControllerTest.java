package com.study.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.sns.controller.request.UserJoinRequestDto;
import com.study.sns.controller.request.UserLoginRequest;
import com.study.sns.exception.SnsApplicationException;
import com.study.sns.fixture.UserFixture;
import com.study.sns.model.dto.UserDto;
import com.study.sns.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
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
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void 회원가입() throws Exception {
        String email = "tester@email.com";
        String password = "testerPw1234!";

        when(userService.join(email, password))
                .thenReturn(
                        UserDto.fromEntity(
                                UserFixture.get(
                                        email,
                                        password
                                )
                        )
                );

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequestDto(email, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 이미 가입된 이메일로 회원 가입을 시도하는 경우")
    void 회원가입실패_존재하는_아이디() throws Exception {
        String email = "tester@email.com";
        String password = "testerPw1234!";

        when(userService.join(email, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequestDto(email, password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void 로그인() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testPw1234!";
        // When
        when(userService.login(email, password)).thenReturn("test_token");

        // Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 존재하지 않는 email로 로그인 하는 경우")
    void 로그인실패_존재하지않는_회원() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testPw1234!";
        // When
        when(userService.login(email, password)).thenThrow(new SnsApplicationException());

        // Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 일치하지 않는 비밀번호로 로그인 하는 경우")
    void 로그인실패_일치하지않는_패스워드() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testPw1234!";
        // When
        when(userService.login(email, password)).thenThrow(new SnsApplicationException());

        // Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(email, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
