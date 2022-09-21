package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.TokenDto;
import com.sparta.innovationfinal.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

public class MemberService {

    @Transactional
    public ResponseDto<?> login(LoginRequstDto requstDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getEmail());
        if(member == null) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if(!member.validatePssword(passwordEncoder, requstDto.getPasswrod())) {
            return ResponseDto.fail(ErrorCode.INVALD_MEMBER);
        }
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success("success login");
    }
}
