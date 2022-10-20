package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.LoginRequestDto;
import com.sparta.innovationfinal.dto.requestDto.EmailCheckDto;
import com.sparta.innovationfinal.dto.requestDto.MemberRequestDto;
import com.sparta.innovationfinal.dto.requestDto.NickNameCheckDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"멤버 CRUD API"})
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/members/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return memberService.createMember(memberRequestDto);
    }

    @PostMapping(value = "/members/signup/email")
    public ResponseDto<?> emailCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        return memberService.checkEmailDuplicate(emailCheckDto);
    }

    @PostMapping(value = "/members/signup/nickname")
    public ResponseDto<?> nicknameCheck(@RequestBody @Valid NickNameCheckDto nickNameCheckDto) {
        return memberService.checkNicknameDuplicate(nickNameCheckDto);
    }

    @PostMapping(value = "/members/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    @DeleteMapping(value = "/auth/members/leave")
    public ResponseDto<?> deleteMember(HttpServletRequest request) {
        return memberService.deleteMember(request);
    }
}
