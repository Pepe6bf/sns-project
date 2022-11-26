package com.study.sns.service;

import com.study.sns.dto.UserDto;
import com.study.sns.global.exception.AccountErrorCode;
import com.study.sns.global.exception.SnsApplicationException;
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

    @Transactional
    public UserDto join(
            String email,
            String password
    ) {
        // 이미 가입된 email 인지 검증
        userRepository
                .findByEmail(email)
                .ifPresent(it -> {
                    throw new SnsApplicationException(AccountErrorCode.DUPLICATED_USER_EMAIL);
                });

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

    // TODO : implement
//    public String login(
//            String email,
//            String password
//    ) {
//        // 회원가입 여부 체크
//        User userEntity = userRepository.findByEmail(email).orElseThrow(SnsApplicationException::new);
//
//        // 비밀번호 체크
//        if (!userEntity.getPassword().equals(password)) {
//            throw new SnsApplicationException();
//        }
//
//        // 토큰 생성
//
//        return "token";
//    }
}
