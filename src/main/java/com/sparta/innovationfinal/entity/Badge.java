package com.sparta.innovationfinal.entity;

import com.sparta.innovationfinal.dto.responseDto.BadgeResponseDto;
import lombok.*;

import javax.persistence.*;
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String badgeName;

    @Column(nullable = false)
    private String badgeInfo;

    @Column(nullable = false)
    private String badgeIcon;

    public Badge(BadgeResponseDto add) {
        this.badgeName = add.getBadgeName();
        this.badgeInfo = add.getBadgeInfo();
        this.badgeIcon = add.getBadgeIcon();
    }

}
