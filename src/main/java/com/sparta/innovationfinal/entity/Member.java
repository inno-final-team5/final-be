package com.sparta.innovationfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.innovationfinal.dto.requestDto.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    public Member(MemberRequestDto memberRequestDto, String password) {
        this.email = memberRequestDto.getEmail();
        this.nickname = memberRequestDto.getNickname();
        this.password = password;
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

}
