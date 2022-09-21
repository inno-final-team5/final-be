package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.CheckDto;
import com.sparta.innovationfinal.dto.requestDto.MemberRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/api/members/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return memberService.createMember(memberRequestDto);
    }

    @PostMapping(value = "/api/members/signup/email")
    public ResponseDto<?> emailCheck(@RequestBody @Valid CheckDto emailCheck) {
        return memberService.checkEmailDuplicate(emailCheck);
    }

    @PostMapping(value = "/api/members/signup/nickname")
    public ResponseDto<?> nicknameCheck(@RequestBody @Valid CheckDto nicknameCheck) {
        return memberService.checkNicknameDuplicate(nicknameCheck);
    }

}
