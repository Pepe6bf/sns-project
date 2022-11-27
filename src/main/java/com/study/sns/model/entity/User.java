package com.study.sns.model.entity;

import com.study.sns.global.config.audit.BaseTimeEntity;
import com.study.sns.model.constant.UserRole;
import com.study.sns.model.constant.UserStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")   // postgre를 사용할 경우 \"해줘야 함
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Getter
    private String email;

    @Column(nullable = false)
    @Getter
    private String password;

    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private UserStatus status = UserStatus.ACTIVE;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User of(
            String email,
            String password
    ) {
        return new User(email, password);
    }
}
