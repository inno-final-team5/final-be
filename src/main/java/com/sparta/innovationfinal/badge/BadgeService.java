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
    public ResponseDto<?> getBadge() {
        List<Badge> badgeList = badgeRepository.findAll();
        List<BadgeResponseDto> badgeResponseDtoList = new ArrayList<>();
        for (Badge badge : badgeList) {
            badgeResponseDtoList.add(
                    BadgeResponseDto.builder()
                            .badgeName(badge.getBadgeName())
                            .badgeInfo(badge.getBadgeInfo())
                            .badgeIcon(badge.getBadgeIcon())
                            .build()
            );
        }
        return ResponseDto.success(badgeResponseDtoList);
    }
}
