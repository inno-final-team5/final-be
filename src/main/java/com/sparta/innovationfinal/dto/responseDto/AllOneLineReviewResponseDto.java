package com.sparta.innovationfinal.dto.responseDto;

import com.sparta.innovationfinal.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllOneLineReviewResponseDto {
    private Long movieId;
    private String title;
    private String posterPath;
    private Long reviewId;
    private String nickname;
    private int oneLineReviewStar;
    private int likeNum;
    private String oneLineReviewContent;
    private String createdAt;
    private String modifiedAt;
}
