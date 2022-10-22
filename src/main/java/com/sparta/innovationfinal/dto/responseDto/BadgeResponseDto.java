package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeResponseDto {
    private Long badgeId;
    private String badgeName;
    private String badgeIcon;
    private String badgeInfo;
    private int badgeTotal;
    private int memberTotal;
    private int postTotal;
    private int oneReviewTotal;
    private int oneReviewLikeNumTotal;
    private int postLikeNumTotal;
    private int favoriteTotal;
    private int reviewStarFiveTotal;
    private int reviewStarOneTotal;
    private int getBadgeTotal;
    private int badgeNum;
}
