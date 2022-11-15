package com.study.sns.repository;

import com.study.sns.model.entity.User;
import com.study.sns.model.UserJoinResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
