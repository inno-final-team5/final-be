package com.sparta.innovationfinal.badge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeResponseDto {
    private String badgeName;
    private String badgeIcon;
    private String badgeInfo;
    private Boolean badgeCondition;
}