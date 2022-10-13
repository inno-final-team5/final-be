package com.sparta.innovationfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.innovationfinal.dto.requestDto.MemberRequestDto;
import com.sparta.innovationfinal.dto.requestDto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped{

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

    @Column(unique = true)
    private Long kakaoId;

    @Column(nullable = false)
    private Long mainBadge = 0L;

    public void update(Long id) {
        this.mainBadge = id;
    }


    public Member(MemberRequestDto memberRequestDto, String password) {
        this.email = memberRequestDto.getEmail();
        this.nickname = memberRequestDto.getNickname();
        this.password = password;
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public boolean validateMember(Member member) {
        return this.id == (member.getId());
    }

    
    public Member(String email, String nickname) {
        this.password = "kakao user";
        this.email = email;
        this.nickname = nickname;
        this.kakaoId = null;
    }
}
