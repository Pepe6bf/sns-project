package com.study.sns.service;

import com.study.sns.exception.SnsApplicationException;
import com.study.sns.model.entity.User;
import com.study.sns.model.UserJoinResponseDto;
import com.study.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // TODO : implement
    public UserJoinResponseDto join(String email,
                                    String password
    ) {
        // 회원가입하려는 email로 회원가입이된 user가 있는지 검증
        userRepository
                .findByEmail(email)
                .ifPresent(it -> {
                    throw new SnsApplicationException();
                });

        // 회원가입 진행 = user를 등록
        userRepository.save(new User());

        return new UserJoinResponseDto();
    }

    // TODO : implement
    public String login(String email,
                        String password
    ) {
        // 회원가입 여부 체크
        User userEntity = userRepository.findByEmail(email).orElseThrow(SnsApplicationException::new);

        // 비밀번호 체크
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        // 토큰 생성

        return "token";
    }
}
