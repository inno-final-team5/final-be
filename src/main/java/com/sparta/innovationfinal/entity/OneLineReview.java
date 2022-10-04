package com.sparta.innovationfinal.entity;


import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import com.sparta.innovationfinal.dto.requestDto.OneLineReviewRequestDto;
import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OneLineReview extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne (fetch = FetchType.LAZY)
    private Member member;
    @JoinColumn(name = "movie_id", nullable = false)
    @ManyToOne (fetch = FetchType.LAZY)
    private Movie movie;
    @Column
    private String oneLineReviewContent;
    @Column
    private int oneLineReviewStar;
    @Column
    private int likeNum;


    public void update(OneLineReviewRequestDto requestDto) {
        this.oneLineReviewContent = requestDto.getOneLineReviewContent() ;
        this.oneLineReviewStar = requestDto.getOneLineReviewStar();
    }

}
