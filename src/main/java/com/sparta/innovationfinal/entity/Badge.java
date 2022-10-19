package com.sparta.innovationfinal.entity;

import com.sparta.innovationfinal.dto.responseDto.BadgeResponseDto;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String badgeName;

    @Column(nullable = false)
    private String badgeInfo;

    @Column(nullable = false)
    private String badgeIcon;

    @Column(nullable = false)
    private int badgeCount;

    @Column(nullable = false)
    private int memberTotal;


    public Badge(BadgeResponseDto add) {
        this.badgeName = add.getBadgeName();
        this.badgeInfo = add.getBadgeInfo();
        this.badgeIcon = add.getBadgeIcon();

    }

    public void update(int size) {
        this.badgeCount = size;
    }
}
