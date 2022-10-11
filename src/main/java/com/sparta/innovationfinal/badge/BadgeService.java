package com.sparta.innovationfinal.badge;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.jwt.TokenProvider;
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
        return ResponseDto.success(responseDtoList);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
