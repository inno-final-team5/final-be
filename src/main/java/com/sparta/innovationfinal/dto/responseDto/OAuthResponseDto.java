package com.sparta.innovationfinal.dto.responseDto;

import com.sparta.innovationfinal.dto.TokenDto;
import com.sparta.innovationfinal.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthResponseDto {
    private String email;
    private String username;
    private String accessToken;
    private String refreshToken;
    private String kakaoToken;
    private Long badgeId;

    public OAuthResponseDto(Member kakaoUser, TokenDto tokenDto, String kakaoToken) {
        this.email = kakaoUser.getEmail();
        this.username = kakaoUser.getNickname();
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
        this.kakaoToken = kakaoToken;
        this.badgeId = kakaoUser.getMainBadge();
    }

}