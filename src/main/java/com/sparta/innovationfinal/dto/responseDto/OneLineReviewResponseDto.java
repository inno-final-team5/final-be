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
public class OneLineReviewResponseDto {
    private Long oneLineReviewId;
    private Long movieId;
    private String title;
    private String posterPath;
    private String nickname;
    private Long badgeId;
    private int oneLineReviewStar;
    private String oneLineReviewContent;
    private String createdAt;
    private String modifiedAt;
}
