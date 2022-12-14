package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.TokenDto;
import com.sparta.innovationfinal.dto.requestDto.EmailCheckDto;
import com.sparta.innovationfinal.dto.requestDto.LoginRequestDto;
import com.sparta.innovationfinal.dto.requestDto.MemberRequestDto;
import com.sparta.innovationfinal.dto.requestDto.NicknameCheckDto;
import com.sparta.innovationfinal.dto.responseDto.BadgeResponseDto;
import com.sparta.innovationfinal.dto.responseDto.MemberActiveResponseDto;
import com.sparta.innovationfinal.dto.responseDto.MemberResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.MemberBadge;
import com.sparta.innovationfinal.dto.ErrorCode;
import com.sparta.innovationfinal.config.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final OneLineReviewLikeRepository oneLineReviewLikeRepository;
    private final OneLineReviewRepository oneLineReviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private static final int badgeNum = 5;

    // 회원가입
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

    // 로그인
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
    public ResponseDto<?> checkEmailDuplicate(EmailCheckDto email) {
        if (isPresentEmail(email.getEmail()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_EMAIL);
        } else {
            return ResponseDto.success("사용가능한 이메일입니다.");
        }
    }

    // 닉네임 중복체크
    public ResponseDto<?> checkNicknameDuplicate(NicknameCheckDto nickname) {
        if (isPresentNickname(nickname.getNickname()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_NICKNAME);
        } else {
            return ResponseDto.success("사용가능한 닉네임입니다.");
        }
    }

    //회원 탈퇴
    public ResponseDto<?> deleteMember(HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        memberRepository.delete(member);
        return ResponseDto.success("delete success");
    }

    //회원 정보 수정 - 닉네임
    public ResponseDto<?> modifyNickname(NicknameCheckDto checkDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        Member findMember = memberRepository.findMemberByEmail(member.getEmail());
        // 내 닉네임과 같을 경우
        if (checkDto.getNickname().equals(findMember.getNickname())) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_MYNICKNAME);
        }
        // 닉네임 중복검사
        if (isPresentNickname(checkDto.getNickname()) != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_NICKNAME);
        }

        findMember.updateNickname(checkDto.getNickname());

        return ResponseDto.success(checkDto.getNickname());

    }

    // 나의 활동정보 조회
    public ResponseDto<?> getMyActiveInfo(HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        List<BadgeResponseDto> responseDtoList = new ArrayList<>();
        List<MemberBadge> memberBadges = memberBadgeRepository.findMemberBadgeByMember(member);

        for (MemberBadge memberBadge : memberBadges) {

            responseDtoList.add(BadgeResponseDto.builder()
                    .badgeId(memberBadge.getBadge().getId())
                    .badgeIcon(memberBadge.getBadge().getBadgeIcon())
                    .badgeName(memberBadge.getBadge().getBadgeName())
                    .badgeInfo(memberBadge.getBadge().getBadgeInfo())
                    .memberTotal(memberRepository.findAll().size())
                    .badgeTotal(memberBadges.size())
                    .build()
            );
        }

            List<MemberActiveResponseDto> memberActiveResponseDtos = new ArrayList<>();
            memberActiveResponseDtos.add(MemberActiveResponseDto.builder()
                    .postTotal(postRepository.findPostByMember(member).size())
                    .oneReviewTotal(oneLineReviewRepository.findOneLineReviewByMember(member).size())
                    .oneReviewLikeNumTotal(oneLineReviewLikeRepository.findOneLineReviewLikeByMember(member).size())
                    .postLikeNumTotal(postLikeRepository.findPostLikeByMember(member).size())
                    .favoriteTotal(favoriteRepository.findFavoriteByMember(member).size())
                    .reviewStarOneTotal(oneLineReviewRepository.findOneLineReviewByMemberAndOneLineReviewStar(member, 1).size())
                    .reviewStarFiveTotal(oneLineReviewRepository.findOneLineReviewByMemberAndOneLineReviewStar(member, 5).size())
                    .getBadgeTotal(memberBadgeRepository.findMemberBadgeByMember(member).size())
                    .badgeNum(badgeNum)
                    .badgeResponseDtoList(responseDtoList)
                    .build());
        return ResponseDto.success(memberActiveResponseDtos);
        }


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

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
    }

}
