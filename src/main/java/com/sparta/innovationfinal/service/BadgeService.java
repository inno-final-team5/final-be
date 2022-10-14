package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.responseDto.BadgeResponseDto;
import com.sparta.innovationfinal.entity.MemberBadge;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Badge;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.BadgeRepository;
import com.sparta.innovationfinal.repository.MemberBadgeRepository;
import com.sparta.innovationfinal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    // 전체 배지 조회
    @Transactional
    public ResponseDto<?> getAllBadge() {
        List<Badge> badgeList = badgeRepository.findAll();
        List<BadgeResponseDto> allBadgeResponseDto = new ArrayList<>();
        for (Badge badge : badgeList) {
            allBadgeResponseDto.add(
                    BadgeResponseDto.builder()
                            .badgeId(badge.getId())
                            .badgeIcon(badge.getBadgeIcon())
                            .badgeName(badge.getBadgeName())
                            .badgeInfo(badge.getBadgeInfo())
                            .build()
            );
        }
        return ResponseDto.success(allBadgeResponseDto);
    }

    // 마이페이지 나의 배지 조회
    @Transactional
    public ResponseDto<?> getMyBadge(HttpServletRequest request) {
        Member member = validateMember(request);
        List<BadgeResponseDto> responseDtoList = new ArrayList<>();
        List<MemberBadge> memberBadges = memberBadgeRepository.findMemberBadgeByMember(member);

        for (MemberBadge memberBadge : memberBadges) {
            responseDtoList.add(BadgeResponseDto.builder()
                    .badgeId(memberBadge.getBadge().getId())
                    .badgeIcon(memberBadge.getBadge().getBadgeIcon())
                    .badgeName(memberBadge.getBadge().getBadgeName())
                    .badgeInfo(memberBadge.getBadge().getBadgeInfo())
                    .build()
            );
        }

        // 모든배지 획득(배지 개수가 7개)일 시 배지 부여(8번배지)
        Badge badge = badgeRepository.findBadgeByBadgeName("배지 마스터");
        MemberBadge findMemberBadge = memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge);
        if (memberBadges.size() == 7 && findMemberBadge == null) {
            // 맴버배지 테이블에 저장
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();

            memberBadgeRepository.save(memberBadge);

        }

        return ResponseDto.success(responseDtoList);
    }

    // 대표 배지 설정
    @Transactional
    public ResponseDto<?> addMainBadge(Long id, HttpServletRequest request) {
        // 로그인 예외처리
        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = validateMember(request);
        if (member == null) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }

        Badge badge = badgeRepository.findBadgeById(id);
        MemberBadge memberBadge = memberBadgeRepository.findMemberBadgeByBadgeAndMember(badge, member);

        if (memberBadge == null) {
            return ResponseDto.fail(ErrorCode.INVALID_BADGE);
        }
        Member findMember = memberRepository.findMemberByEmail(member.getEmail());
        findMember.update(id);

        return ResponseDto.success(BadgeResponseDto.builder()
                .badgeId(badge.getId())
                .badgeName(badge.getBadgeName())
                .badgeIcon(badge.getBadgeIcon())
                .badgeInfo(badge.getBadgeInfo())
                .build()
        );
    }

    // 대표 배지 취소
    @Transactional
    public ResponseDto<?> cancelMainBadge(HttpServletRequest request) {
        // 로그인 예외처리
        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = validateMember(request);
        if (member == null) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }
        Member findMember = memberRepository.findMemberByEmail(member.getEmail());
        if (member.getMainBadge() == 0) {
            return ResponseDto.fail(ErrorCode.INVALID_MAINBADGE);
        }
        findMember.update(0L);

        return ResponseDto.success("success delete");
    }

    // 대표 배지 조회

    @Transactional
    public ResponseDto<?> getMainBadge(HttpServletRequest request) {
        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = validateMember(request);
        if (member == null) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }
        Member findMember = memberRepository.findMemberByEmail(member.getEmail());
        if (findMember.getMainBadge() == 0) {
            return ResponseDto.success("badgeId : 0");
        }
        Long findMainBadge = findMember.getMainBadge();
        Badge badge = badgeRepository.findBadgeById(findMainBadge);
        MemberBadge memberBadge = memberBadgeRepository.findMemberBadgeByBadgeAndMember(badge, member);

        return ResponseDto.success(BadgeResponseDto.builder()
                .badgeId(memberBadge.getBadge().getId())
                .badgeName(memberBadge.getBadge().getBadgeName())
                .badgeIcon(memberBadge.getBadge().getBadgeIcon())
                .badgeInfo(memberBadge.getBadge().getBadgeInfo())
                .build());
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
