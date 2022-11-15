package com.study.sns.model.entity;

import com.study.sns.model.constant.UserRole;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "\"user\"")   // postgre를 사용할 경우 \"해줘야 함
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "email")
    @Getter
    private String email;

    @Column(name = "password")
    @Getter
    private String password;

//    private static User of() {
//
//    }

}
