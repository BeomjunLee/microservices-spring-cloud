package com.msa.userservice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String name;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }
}
