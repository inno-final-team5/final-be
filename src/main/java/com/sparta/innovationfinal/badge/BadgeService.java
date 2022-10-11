package com.sparta.innovationfinal.badge;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;

    // 전체 배지 조회
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

}
