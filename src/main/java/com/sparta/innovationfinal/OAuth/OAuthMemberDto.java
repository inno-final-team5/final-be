package com.sparta.innovationfinal.OAuth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthMemberDto {
    private Long id;
    private String email;
    private String nickname;
}