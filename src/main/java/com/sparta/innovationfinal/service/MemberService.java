package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.TokenDto;
import com.sparta.innovationfinal.dto.requestDto.EmailCheckDto;
import com.sparta.innovationfinal.dto.requestDto.LoginRequestDto;
import com.sparta.innovationfinal.dto.requestDto.MemberRequestDto;
import com.sparta.innovationfinal.dto.requestDto.NickNameCheckDto;
import com.sparta.innovationfinal.dto.responseDto.MemberResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

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

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .memberId(member.getId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .badgeId(member.getMainBadge())
                        .createdAt(String.valueOf(member.getCreatedAt()))
                        .modifiedAt(String.valueOf(member.getModifiedAt()))
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentEmail(requestDto.getEmail());
        if (member == null) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .badgeId(member.getMainBadge())
                .createdAt(String.valueOf(member.getCreatedAt()))
                .modifiedAt(String.valueOf(member.getModifiedAt()))
                .build()
        );
    }

    // 이메일 중복체크
    @Transactional
    public ResponseDto<?> checkEmailDuplicate(EmailCheckDto email) {
        if (isPresentEmail(email.getEmail()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_EMAIL);
        } else {
            return ResponseDto.success("사용가능한 이메일입니다.");
        }
    }

    // 닉네임 중복체크
    @Transactional
    public ResponseDto<?> checkNicknameDuplicate(NickNameCheckDto nickname) {
        if (isPresentNickname(nickname.getNickname()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_NICKNAME);
        } else {
            return ResponseDto.success("사용가능한 닉네임입니다.");
        }
    }

//    //회원 탈퇴
    @Transactional
    public ResponseDto<?> deleteMember(HttpServletRequest request) {

        Member member = validateMember(request);

        memberRepository.delete(member);
        return ResponseDto.success("delete success");
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Member isPresentEmail(String email) {
        return (memberRepository.findByEmail(email)).orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentNickname(String nickname) {
        return (memberRepository.findByNickname(nickname)).orElse(null);
    }

    @Transactional
    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
    }

    @Transactional
    public ResponseDto<?> checkMember(HttpServletRequest request) {
        if(null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.NULL_TOKEN);
        }

        if(null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.NULL_TOKEN);
        }

        if(!tokenProvider.validateToken(request.getHeader("Refresh-Token")))
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);

        Member member = tokenProvider.getMemberFromAuthentication();

        return ResponseDto.success(member);
    }


}
