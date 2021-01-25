package com.joon.springsecurityproject.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Account {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.setPassword(passwordEncoder.encode(password));
    }
}
