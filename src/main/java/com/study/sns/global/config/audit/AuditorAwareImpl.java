package com.study.sns.global.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 현재 로그인한 사용자를 데이터의 생성/수정자로 등록하는 역할을 담당
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null) {
            email = authentication.getName();
        }

        return Optional.of(email);
    }
}
