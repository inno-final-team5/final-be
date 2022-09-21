package com.sparta.innovationfinal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.innovationfinal.dto.requestDto.CheckDto;
import com.sparta.innovationfinal.dto.requestDto.MemberRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto memberRequestDto) {
        if (isPresentEmail(memberRequestDto.getEmail()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_EMAIL);
        }
        if (isPresentNickname(memberRequestDto.getNickname()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_NICKNAME);
        }

        String pw = passwordEncoder.encode(memberRequestDto.getPassword());

        Member member = new Member(memberRequestDto, pw);
        memberRepository.save(member);

        return ResponseDto.success("signup Success");
    }

    // 이메일 중복체크
    @Transactional
    public ResponseDto<?> checkEmailDuplicate(CheckDto email) {
        if (isPresentEmail(email.getEmail()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_EMAIL);
        } else {
            return ResponseDto.success("사용가능한 이메일입니다.");
        }
    }

    // 닉네임 중복체크
    @Transactional
    public ResponseDto<?> checkNicknameDuplicate(CheckDto nickname) {
        if (isPresentNickname(nickname.getNickname()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_NICKNAME);
        } else {
            return ResponseDto.success("사용가능한 닉네임입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Member isPresentEmail(String email) {
        return (memberRepository.findByEmail(email)).orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentNickname(String nickname) {
        return (memberRepository.findByNickname(nickname)).orElse(null);
    }


}
