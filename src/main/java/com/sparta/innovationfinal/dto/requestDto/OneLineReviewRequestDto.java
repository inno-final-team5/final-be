package com.sparta.innovationfinal.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OneLineReviewRequestDto {
    private Long movieId;
    private String title;
    private String posterPath;
    private String oneLineReviewContent;
    private int oneLineReviewStar;
}
