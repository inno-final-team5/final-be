package com.sparta.innovationfinal.entity;

import com.sparta.innovationfinal.dto.requestDto.OneLineReviewRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "oneLineReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneLineReviewLike> oneLineReviewLikeList;


    public void update(OneLineReviewRequestDto requestDto) {
        this.oneLineReviewContent = requestDto.getOneLineReviewContent() ;
        this.oneLineReviewStar = requestDto.getOneLineReviewStar();
    }

}
