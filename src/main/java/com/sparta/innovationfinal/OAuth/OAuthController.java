package com.sparta.innovationfinal.OAuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final KakaoService kakaoService;

    @GetMapping("/api/oauth/kakao")
    public ResponseDto<?> callBackKakao(@RequestParam(name = "code") String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }

    @GetMapping("/api/oauth/kakao/logout")
    public ResponseDto<?> logout(HttpServletRequest request){
        return kakaoService.logout(request);
    }

}