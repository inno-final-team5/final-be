package com.sparta.innovationfinal.dto.responseDto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Data
public class MemberActiveResponseDto {
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
