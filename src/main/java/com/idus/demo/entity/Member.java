package com.idus.demo.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * CREATE TABLE `member` (
 * 	`id` BIGINT NOT NULL AUTO_INCREMENT,
 * 	`name` VARCHAR(20) NOT NULL,
 * 	`nickname` VARCHAR(30) NOT NULL,
 * 	`password` VARCHAR(100) NOT NULL,
 * 	`phone` VARCHAR(20) NOT NULL,
 * 	`email` VARCHAR(100) NOT NULL,
 * 	`gender` CHAR(1) NULL,
 * 	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 * 	PRIMARY KEY (`id`)
 * )
 * COLLATE='utf8_general_ci'
 * ENGINE=InnoDB
 */
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "nickname", "phone", "email", "gender", "createdAt"})
public class Member implements UserDetails {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String nickname;

    private String password;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime createdAt;

    public Member(String name, String nickname, String password, String phone, String email, Gender gender) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
