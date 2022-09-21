package com.sparta.innovationfinal.jwt;

import com.sparta.innovationfinal.entity.Member;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetailsImpl {
    private Member member;
    public void setAccount(Member kakaoUser) {
        this.member = kakaoUser;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
