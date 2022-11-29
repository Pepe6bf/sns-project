package com.study.sns.service;

import com.study.sns.dto.UserDto;
import com.study.sns.global.exception.AccountErrorCode;
import com.study.sns.global.exception.SnsApplicationException;
import com.study.sns.jwt.JwtService;
import com.study.sns.model.entity.User;
import com.study.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * 회원가입을 수행하는 비즈니스 로직
     */
    @Transactional
    public UserDto join(
            String email,
            String password
    ) {
        // 이미 가입된 email 인지 검증
        checkUserExist(email);

        // 회원가입 진행 및 응답
        return UserDto.fromEntity(
                userRepository.save(
                        User.of(
                                email,
                                passwordEncoder.encode(password)
                        )
                )
        );
    }

    /**
     * 로그인을 수행하는 비즈니스 로직
     */
    public String login(
            String email,
            String password
    ) {
        // 회원가입 여부 체크
        User userEntity = loadUserByEmail(email);

        // 비밀번호 체크
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new SnsApplicationException(AccountErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        return jwtService.generateToken(email);
    }

    // 존재하는 사용자인지 검증
    private void checkUserExist(String email) {
        userRepository
                .findByEmail(email)
                .ifPresent(it -> {
                    throw new SnsApplicationException(AccountErrorCode.DUPLICATED_USER_EMAIL);
                });
    }

    // 주어진 이메일의 사용자 객체를 로드하는 메서드
    public User loadUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new SnsApplicationException(AccountErrorCode.USER_NOT_FOUND)
                );
    }
}
