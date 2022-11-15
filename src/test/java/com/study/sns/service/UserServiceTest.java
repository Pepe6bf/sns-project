package com.study.sns.service;

import com.study.sns.exception.SnsApplicationException;
import com.study.sns.fixture.UserFixture;
import com.study.sns.model.User;
import com.study.sns.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void 회원가입이_정상적으로_동작하는_경우() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testerPw1234!";

        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(Optional.of(UserFixture.get(email, password)));

        // Then
        Assertions.assertDoesNotThrow(() -> userService.join(email, password));
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 이미 존재하는 email인 경우")
    void 회원가입시_email이_존재하는_경우() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testerPw1234!";

        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(UserFixture.get(email, password)));
        when(userRepository.save(any())).thenReturn(Optional.of(UserFixture.get(email, password)));

        // Then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(email, password));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void 로그인_성공() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testerPw1234!";
        User fixtureUserEntity = UserFixture.get(email, password);

        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(fixtureUserEntity));

        // Then
        Assertions.assertDoesNotThrow(() -> userService.login(email, password));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 존재하지 않는 email일 경우")
    void 로그인시_email로_회원가입한_사용자가_없는_경우() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testerPw1234!";
        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(email, password));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 일치하지 않는 password일 경우")
    void 로그인시_패스워드_틀린_경우() throws Exception {
        // Given
        String email = "tester@email.com";
        String password = "testerPw1234!";
        String wrongPassword = "bammmmm";

        // When
        User fixture = UserFixture.get(email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(fixture));

        // Then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(email, wrongPassword));
    }
}