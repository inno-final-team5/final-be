package com.sparta.innovationfinal.jwt;

import com.sparta.innovationfinal.dto.TokenDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.shared.Authority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenProvider {
    public TokenDto generateTokenDto(Member kakaoUser) {
        long now = (new Date().getTime());

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(member.getEmail())                           // payload "sub" : "name"
                .claim(AUTHORITIES_KEY, Authority.ROLE_MEMBER.toString())   // payload "auth" : "ROLE_MEMBER"
                .setExpiration(accessTokenExpiresIn)                        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS256)                    // header "alg": "HS256"
                .compact();

        // refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPRIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        RefreshToken refreshTokenObject = RefreshToken.builder()
                .id(member.getId())
                .member(member)
                .value(refreshToken)
                .build();

        refreshTokenRepository.save(refreshTokenObject);

        return TokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();

    }


}

