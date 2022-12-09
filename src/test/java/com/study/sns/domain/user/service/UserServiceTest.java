package com.study.sns.domain.user.service;

import com.study.sns.domain.user.account.jwt.JwtService;
import com.study.sns.domain.user.account.jwt.exception.AccountErrorCode;
import com.study.sns.domain.user.dto.LocalLoginServiceDto;
import com.study.sns.domain.user.dto.UserDto;
import com.study.sns.domain.user.dto.UserJoinServiceDto;
import com.study.sns.domain.user.model.constant.UserRole;
import com.study.sns.domain.user.model.constant.UserStatus;
import com.study.sns.domain.user.model.entity.User;
import com.study.sns.domain.user.repository.UserRepository;
import com.study.sns.global.exception.SnsApplicationException;
import com.study.sns.global.util.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtService jwtService;

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
        given(userRepository.save(any(User.class)))
                .willReturn(
                        UserFixture.get(
                                dto.getEmail(),
                                passwordEncoder.encode(dto.getPassword()))
                );

        // When
        UserDto userDto = userService.join(dto);

        // Then
        assertThat(userDto.getEmail()).isEqualTo(dto.getEmail());
        assertThat(userDto.getRole()).isEqualTo(UserRole.USER);
        assertThat(userDto.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("회원가입 실패 테스트 - 이미 존재하는 email인 경우")
    void 회원가입시_email이_존재하는_경우() throws Exception {
        // Given
        UserJoinServiceDto dto = new UserJoinServiceDto(
                "tester@email.com",
                "testerPw1234!"
        );
        given(userRepository.findByEmail(dto.getEmail()))
                .willReturn(Optional.of(
                        UserFixture.get(
                                dto.getEmail(),
                                passwordEncoder.encode(dto.getPassword())
                        )
                ));
        given(userRepository.save(any(User.class)))
                .willReturn(
                        UserFixture.get(
                                dto.getEmail(),
                                passwordEncoder.encode(dto.getPassword()))
                );

        // When
        SnsApplicationException e = catchThrowableOfType(
                () -> {
                    userService.join(dto);
                },
                SnsApplicationException.class
        );

        // Then
        assertThat(e.getErrorCode()).isEqualTo(AccountErrorCode.DUPLICATED_USER_EMAIL);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void 로그인_성공() throws Exception {
        // Given
        LocalLoginServiceDto localLoginDto = new LocalLoginServiceDto(
                "tester@email.com",
                "testPw1234!"
        );
        given(userRepository.findByEmail(localLoginDto.getEmail()))
                .willReturn(Optional.of(
                        UserFixture.get(
                                localLoginDto.getEmail(),
                                passwordEncoder.encode(localLoginDto.getPassword())))
                );
        given(jwtService.generateToken(localLoginDto.getEmail()))
                .willReturn(
                        "valid token"
                );

        // When
        String loginToken = userService.login(localLoginDto);

        // Then
        assertThat(loginToken).isEqualTo("valid token");
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 존재하지 않는 email일 경우")
    void 로그인시_email로_회원가입한_사용자가_없는_경우() throws Exception {
        // Given
        LocalLoginServiceDto localLoginDto = new LocalLoginServiceDto(
                "invalidEmail@email.com",
                "testPw1234!"
        );
        given(userRepository.findByEmail(localLoginDto.getEmail()))
                .willReturn(Optional.empty());
        given(jwtService.generateToken(localLoginDto.getEmail()))
                .willReturn(
                        "valid token"
                );

        // When
        SnsApplicationException e = catchThrowableOfType(
                () -> {
                    userService.login(localLoginDto);
                },
                SnsApplicationException.class
        );

        // Then
        assertThat(e.getErrorCode()).isEqualTo(AccountErrorCode.USER_NOT_FOUND);
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 일치하지 않는 password일 경우")
        void 로그인시_패스워드_틀린_경우() throws Exception {
        LocalLoginServiceDto localLoginDto = new LocalLoginServiceDto(
                "tester@email.com",
                "Invalid Password"
        );
        String validPassword = "testerPw1234!";
        given(userRepository.findByEmail(localLoginDto.getEmail()))
                .willReturn(Optional.of(
                        UserFixture.get(
                                localLoginDto.getEmail(),
                                passwordEncoder.encode(validPassword)))
                );
        given(jwtService.generateToken(localLoginDto.getEmail()))
                .willReturn(
                        "valid token"
                );

        // When
        SnsApplicationException e = catchThrowableOfType(
                () -> {
                    userService.login(localLoginDto);
                },
                SnsApplicationException.class
        );

        // Then
        assertThat(e.getErrorCode()).isEqualTo(AccountErrorCode.INVALID_PASSWORD);
    }
}