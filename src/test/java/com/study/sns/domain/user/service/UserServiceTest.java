package com.study.sns.domain.user.service;

import com.study.sns.domain.user.dto.UserDto;
import com.study.sns.domain.user.dto.UserJoinServiceDto;
import com.study.sns.domain.user.model.constant.UserRole;
import com.study.sns.domain.user.model.constant.UserStatus;
import com.study.sns.domain.user.repository.UserRepository;
import com.study.sns.global.util.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void 회원가입이_정상적으로_동작하는_경우() throws Exception {
        // Given
        UserJoinServiceDto dto = new UserJoinServiceDto(
                "tester@email.com",
                "testerPw1234!"
        );
        given(userRepository.findByEmail(dto.getEmail()))
                .willReturn(Optional.empty());
        given(passwordEncoder.encode(dto.getPassword()))
                .willReturn("encrypt_password");
        given(userRepository.save(any()))
                .willReturn(UserFixture.get(dto.getEmail(), dto.getPassword()));

        // When
        UserDto userDto = userService.join(dto);

        // Then
        assertThat(userDto.getEmail()).isEqualTo(dto.getEmail());
        assertThat(userDto.getRole()).isEqualTo(UserRole.USER);
        assertThat(userDto.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

//    @Test
//    @DisplayName("회원가입 실패 테스트 - 이미 존재하는 email인 경우")
//    void 회원가입시_email이_존재하는_경우() throws Exception {
//        // Given
//        UserJoinDto.Request req = new UserJoinDto.Request(
//                "tester@email.com",
//                "testerPw1234!");
//
//        // When
//        when(userRepository.findByEmail(req.getEmail()))
//                .thenReturn(Optional.of(
//                        UserFixture.get(
//                                req.getEmail(),
//                                req.getPassword()
//                        )));
//        when(passwordEncoder.encode(req.getPassword()))
//                .thenReturn("encrypt_password");
//        when(userRepository.save(any()))
//                .thenReturn(Optional.of(
//                        UserFixture.get(
//                                req.getEmail(),
//                                req.getPassword()
//                        )));
//
//        // Then
//        SnsApplicationException e = Assertions.assertThrows(
//                SnsApplicationException.class,
//                () -> userService.join(req));
//        Assertions.assertEquals(
//                AccountErrorCode.DUPLICATED_USER_EMAIL,
//                e.getErrorCode());
//    }

//    @Test
//    @DisplayName("로그인 성공 테스트")
//    void 로그인_성공() throws Exception {
//        // Given
//        LocalLoginDto.Request req = new LocalLoginDto.Request(
//                "tester@email.com",
//                "testPw1234!"
//        );
//        User fixtureUserEntity = UserFixture.get(
//                req.getEmail(),
//                req.getPassword()
//        );
//
//        // When
//        when(userRepository.findByEmail(req.getEmail()))
//                .thenReturn(Optional.of(fixtureUserEntity));
//        when(passwordEncoder.matches(
//                req.getPassword(),
//                fixtureUserEntity.getPassword()))
//                .thenReturn(true);
//
//        // Then
//        Assertions.assertDoesNotThrow(
//                () -> userService.login(req)
//        );
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 존재하지 않는 email일 경우")
//    void 로그인시_email로_회원가입한_사용자가_없는_경우() throws Exception {
//        // Given
//        LocalLoginDto.Request req = new LocalLoginDto.Request(
//                "tester@email.com",
//                "testPw1234!"
//        );
//        // When
//        when(userRepository.findByEmail(req.getEmail()))
//                .thenReturn(Optional.empty());
//
//        // Then
//        SnsApplicationException e = Assertions.assertThrows(
//                SnsApplicationException.class,
//                () -> userService.login(req)
//        );
//        Assertions.assertEquals(
//                AccountErrorCode.USER_NOT_FOUND,
//                e.getErrorCode()
//        );
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 일치하지 않는 password일 경우")
//    void 로그인시_패스워드_틀린_경우() throws Exception {
//        // Given
//        LocalLoginDto.Request req = new LocalLoginDto.Request(
//                "tester@email.com",
//                "Wrong Password"
//        );
//        String correctPassword = "testPw1234!";
//
//        // When
//        User fixture = UserFixture.get(
//                req.getEmail(),
//                correctPassword
//        );
//        when(userRepository.findByEmail(req.getEmail()))
//                .thenReturn(Optional.of(fixture));
//
//        // Then
//        SnsApplicationException e = Assertions.assertThrows(
//                SnsApplicationException.class,
//                () -> userService.login(req)
//        );
//        Assertions.assertEquals(
//                AccountErrorCode.INVALID_PASSWORD,
//                e.getErrorCode()
//        );
//    }
}