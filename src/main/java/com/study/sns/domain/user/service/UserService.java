package com.study.sns.domain.user.service;

import com.study.sns.domain.user.dto.LocalLoginServiceDto;
import com.study.sns.domain.user.dto.UserDto;
import com.study.sns.domain.user.account.jwt.exception.AccountErrorCode;
import com.study.sns.domain.user.dto.UserJoinServiceDto;
import com.study.sns.global.exception.SnsApplicationException;
import com.study.sns.domain.user.account.jwt.JwtService;
import com.study.sns.domain.user.model.entity.User;
import com.study.sns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            UserJoinServiceDto req
    ) {
        // 이미 가입된 email 인지 검증
        checkUserExist(req.getEmail());

        // 회원가입 진행 및 응답
        return UserDto.fromEntity(
                userRepository.save(
                        User.of(
                                req.getEmail(),
                                req.getPassword()
                        )
                )
        );
    }

    /**
     * 로그인을 수행하는 비즈니스 로직
     */
    public String login(
            LocalLoginServiceDto req
    ) {
        // 회원가입 여부 체크
        User userEntity = loadUserByEmail(req.getEmail());

        // 비밀번호 체크
        if (!passwordEncoder.matches(req.getPassword(), userEntity.getPassword())) {
            throw new SnsApplicationException(AccountErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        return jwtService.generateToken(req.getEmail());
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

    // 현재 로그인 중인 사용자 entity 를 반환하는 메서드
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return loadUserByEmail(authentication.getName());
    }
}
