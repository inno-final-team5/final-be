package com.sparta.innovationfinal.entity;

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
public class OneLineReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @JoinColumn(name = "movie_id", nullable = false)
//    @ManyToOne (fetch = FetchType.LAZY)
//    private Movie movie;
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne (fetch = FetchType.LAZY)
    private Member member;
    @JoinColumn(name = "oneLineReview_id", nullable = false)
    @ManyToOne (fetch = FetchType.LAZY)
    private OneLineReview oneLineReview;


}
