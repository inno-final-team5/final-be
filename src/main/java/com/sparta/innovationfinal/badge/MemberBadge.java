package com.sparta.innovationfinal.badge;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "badge_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Badge badge;

}
