package com.sparta.innovationfinal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.KakaoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Api(tags = {"kakao API"})
public class OAuthController {

    private final KakaoService kakaoService;

    @PostMapping("/oauth/kakao")
    public ResponseDto<?> callBackKakao(@RequestParam(name = "code") String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }

    @GetMapping("/oauth/kakao/logout")
    public ResponseDto<?> logout(HttpServletRequest request){
        return kakaoService.logout(request);
    }

}