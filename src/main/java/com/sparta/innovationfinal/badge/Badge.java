package com.sparta.innovationfinal.badge;

import com.sparta.innovationfinal.dto.responseDto.BoxofficeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Badge {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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
